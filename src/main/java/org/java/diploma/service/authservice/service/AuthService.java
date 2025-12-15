package org.java.diploma.service.authservice.service;


import org.java.diploma.service.authservice.dto.*;
import org.java.diploma.service.authservice.entity.PasswordResetToken;
import org.java.diploma.service.authservice.entity.RefreshToken;
import org.java.diploma.service.authservice.entity.User;
import org.java.diploma.service.authservice.entity.UserSession;
import org.java.diploma.service.authservice.repository.PasswordResetTokenRepository;
import org.java.diploma.service.authservice.repository.RefreshTokenRepository;
import org.java.diploma.service.authservice.repository.UserRepository;
import org.java.diploma.service.authservice.repository.UserSessionRepository;
import org.java.diploma.service.authservice.security.JwtService;
import org.java.diploma.service.authservice.util.TokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final static String EMAIL_IN_USE = "Email already in use";
    private final static String USERNAME_IN_USE = "Username already in use";
    private final UserRepository users;
    private final RefreshTokenRepository refreshTokens;
    private final UserSessionRepository sessions;
    private final PasswordResetTokenRepository resetTokens;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    private final Duration refreshTtl = Duration.ofDays(30);
    private final Duration resetTtl = Duration.ofMinutes(30);

    public AuthService(
            UserRepository users,
            RefreshTokenRepository refreshTokens,
            UserSessionRepository sessions,
            PasswordResetTokenRepository resetTokens,
            PasswordEncoder encoder,
            JwtService jwt
    ) {
        this.users = users;
        this.refreshTokens = refreshTokens;
        this.sessions = sessions;
        this.resetTokens = resetTokens;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @Transactional
    public void register(RegisterRequest req) {
        users.findByEmailIgnoreCase(req.email()).ifPresent(u -> {
            throw new IllegalArgumentException(EMAIL_IN_USE);
        });
        users.findByUsernameIgnoreCase(req.username()).ifPresent(u -> {
            throw new IllegalArgumentException(USERNAME_IN_USE);
        });

        User u = new User();
        u.setEmail(req.email().trim().toLowerCase());
        u.setUsername(req.username().trim());
        u.setPasswordHash(encoder.encode(req.password()));
        users.save(u);
    }

    @Transactional
    public AuthResponse login(LoginRequest req) {
        User user = resolveUser(req.identifier())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!user.isActive()) throw new IllegalStateException("User inactive");

        if (!encoder.matches(req.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        user.setLastLoginAt(Instant.now());

        String refreshRaw = TokenUtil.newOpaqueToken();
        String refreshHash = TokenUtil.sha256B64Url(refreshRaw);

        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setTokenHash(refreshHash);
        rt.setExpiresAt(Instant.now().plus(refreshTtl));
        rt.setDeviceInfo(req.deviceInfo());
        refreshTokens.save(rt);

        UserSession session = new UserSession();
        session.setUser(user);
        session.setRefreshToken(rt);
        session.setDeviceInfo(req.deviceInfo());
        sessions.save(session);

        String access = jwt.createAccessToken(user.getId(), user.getUsername());
        return new AuthResponse(access, refreshRaw);
    }

    @Transactional
    public AuthResponse refresh(RefreshRequest req) {
        Instant now = Instant.now();
        String oldHash = TokenUtil.sha256B64Url(req.refreshToken());

        RefreshToken old = refreshTokens.findByTokenHash(oldHash)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (!old.isActive(now)) {
            throw new IllegalArgumentException("Refresh token expired/revoked");
        }

        User user = old.getUser();
        if (!user.isActive()) throw new IllegalStateException("User inactive");

        old.setRevokedAt(now);

        String newRaw = TokenUtil.newOpaqueToken();
        String newHash = TokenUtil.sha256B64Url(newRaw);

        RefreshToken next = new RefreshToken();
        next.setUser(user);
        next.setTokenHash(newHash);
        next.setExpiresAt(now.plus(refreshTtl));
        next.setDeviceInfo(req.deviceInfo());
        refreshTokens.save(next);

        old.setReplacedBy(UUID.randomUUID());
        refreshTokens.save(old);

        UserSession session = new UserSession();
        session.setUser(user);
        session.setRefreshToken(next);
        session.setDeviceInfo(req.deviceInfo());
        sessions.save(session);

        String access = jwt.createAccessToken(user.getId(), user.getUsername());
        return new AuthResponse(access, newRaw);
    }

    @Transactional
    public void logout(String refreshTokenRaw) {
        String hash = TokenUtil.sha256B64Url(refreshTokenRaw);
        refreshTokens.findByTokenHash(hash).ifPresent(rt -> {
            rt.setRevokedAt(Instant.now());
            refreshTokens.save(rt);
        });
    }

    @Transactional
    public String createPasswordResetToken(ForgotPasswordRequest req) {
        Optional<User> userOpt = users.findByEmailIgnoreCase(req.email().trim());
        if (userOpt.isEmpty()) return null; // don't leak existence at controller level

        String raw = TokenUtil.newOpaqueToken();
        PasswordResetToken prt = new PasswordResetToken();
        prt.setUser(userOpt.get());
        prt.setTokenHash(TokenUtil.sha256B64Url(raw));
        prt.setExpiresAt(Instant.now().plus(resetTtl));
        resetTokens.save(prt);

        return raw; // in real life: email it, don’t return it
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest req) {
        Instant now = Instant.now();
        String hash = TokenUtil.sha256B64Url(req.token());

        PasswordResetToken token = resetTokens.findByTokenHash(hash)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (!token.isUsable(now)) {
            throw new IllegalArgumentException("Token expired/used");
        }

        User user = token.getUser();
        user.setPasswordHash(encoder.encode(req.newPassword()));
        users.save(user);

        token.setUsedAt(now);
        resetTokens.save(token);

        // optional: revoke all refresh tokens for this user
        // refreshTokens.findAllByUser_Id(user.getId()).forEach(rt -> { rt.setRevokedAt(now); });
    }

    private Optional<User> resolveUser(String identifier) {
        String id = identifier.trim();
        if (id.contains("@")) return users.findByEmailIgnoreCase(id);
        return users.findByUsernameIgnoreCase(id);
    }
}
