package org.java.diploma.service.authservice.service;

import org.java.diploma.service.authservice.dto.*;
import org.java.diploma.service.authservice.entity.PasswordResetToken;
import org.java.diploma.service.authservice.entity.RefreshToken;
import org.java.diploma.service.authservice.entity.User;
import org.java.diploma.service.authservice.repository.PasswordResetTokenRepository;
import org.java.diploma.service.authservice.repository.RefreshTokenRepository;
import org.java.diploma.service.authservice.repository.UserRepository;
import org.java.diploma.service.authservice.repository.UserSessionRepository;
import org.java.diploma.service.authservice.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository users;
    @Mock
    RefreshTokenRepository refreshTokens;
    @Mock
    UserSessionRepository sessions;
    @Mock
    PasswordResetTokenRepository resetTokens;
    @Mock
    PasswordEncoder encoder;
    @Mock
    JwtService jwt;

    @InjectMocks
    AuthService authService;

    @Test
    void register_success() {
        RegisterRequest req = new RegisterRequest(
                "test@mail.com", "testuser", "password123"
        );

        when(users.findByEmailIgnoreCase(any())).thenReturn(Optional.empty());
        when(users.findByUsernameIgnoreCase(any())).thenReturn(Optional.empty());
        when(encoder.encode(any())).thenReturn("hashed");

        authService.register(req);

        verify(users).save(any(User.class));
    }

    @Test
    void register_emailAlreadyUsed() {
        when(users.findByEmailIgnoreCase(any()))
                .thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class,
                () -> authService.register(
                        new RegisterRequest("a@a.com", "u", "password123")
                ));
    }

    @Test
    void login_invalidPassword() {
        User user = new User();
        user.setPasswordHash("hash");
        user.setActive(true);

        when(users.findByEmailIgnoreCase(any())).thenReturn(Optional.of(user));
        when(encoder.matches(any(), any())).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> authService.login(
                        new LoginRequest("a@a.com", "wrong", null)
                ));
    }

    @Test
    void register_fails_whenEmailExists() {
        when(users.findByEmailIgnoreCase(any()))
                .thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () ->
                authService.register(
                        new RegisterRequest("a@a.com", "username", "password123")
                ));
    }

    @Test
    void login_fails_whenUserInactive() {
        User user = new User();
        user.setActive(false);
        user.setPasswordHash("hash");

        when(users.findByEmailIgnoreCase(any()))
                .thenReturn(Optional.of(user));

        assertThrows(IllegalStateException.class, () ->
                authService.login(
                        new LoginRequest("a@a.com", "password", null)
                ));
    }

    @Test
    void login_fails_whenPasswordWrong() {
        User user = new User();
        user.setActive(true);
        user.setPasswordHash("hash");

        when(users.findByEmailIgnoreCase(any()))
                .thenReturn(Optional.of(user));
        when(encoder.matches(any(), any()))
                .thenReturn(false);

        assertThrows(IllegalArgumentException.class, () ->
                authService.login(
                        new LoginRequest("a@a.com", "wrong", null)
                ));
    }

    @Test
    void refresh_fails_whenTokenExpired() {
        RefreshToken rt = new RefreshToken();
        rt.setExpiresAt(Instant.now().minusSeconds(60));

        when(refreshTokens.findByTokenHash(any()))
                .thenReturn(Optional.of(rt));

        assertThrows(IllegalArgumentException.class, () ->
                authService.refresh(
                        new RefreshRequest("token", null)
                ));
    }

    @Test
    void resetPassword_fails_whenTokenInvalid() {
        when(resetTokens.findByTokenHash(any()))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                authService.resetPassword(
                        new ResetPasswordRequest("bad", "password123")
                ));
    }

    @Test
    void refresh_fails_whenTokenNotFound() {
        when(refreshTokens.findByTokenHash(any()))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                authService.refresh(
                        new RefreshRequest("invalid", null)
                ));
    }

    @Test
    void logout_revokesToken_whenExists() {
        RefreshToken token = new RefreshToken();
        token.setExpiresAt(Instant.now().plusSeconds(60));

        when(refreshTokens.findByTokenHash(any()))
                .thenReturn(Optional.of(token));

        authService.logout("raw-token");

        verify(refreshTokens).save(token);
        assertNotNull(token.getRevokedAt());
    }

    @Test
    void createPasswordResetToken_returnsNull_whenUserNotFound() {
        when(users.findByEmailIgnoreCase(any()))
                .thenReturn(Optional.empty());

        String token = authService.createPasswordResetToken(
                new ForgotPasswordRequest("missing@test.com")
        );

        assertNull(token);
    }

    @Test
    void createPasswordResetToken_createsToken_whenUserExists() {
        User user = new User();
        user.setId(1);

        when(users.findByEmailIgnoreCase(any()))
                .thenReturn(Optional.of(user));

        String raw = authService.createPasswordResetToken(
                new ForgotPasswordRequest("a@a.com")
        );

        assertNotNull(raw);
        verify(resetTokens).save(any(PasswordResetToken.class));
    }




}

