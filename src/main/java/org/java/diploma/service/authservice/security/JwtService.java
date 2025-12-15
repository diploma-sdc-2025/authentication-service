package org.java.diploma.service.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final String secret;
    private final long accessTtlSeconds;
    private final String issuer;

    private SecretKey key;

    public JwtService(
            @Value("${auth.jwt.secret}") String secret,
            @Value("${auth.jwt.access-ttl-seconds:900}") long accessTtlSeconds,
            @Value("${auth.jwt.issuer:yourgame-auth}") String issuer
    ) {
        this.secret = secret;
        this.accessTtlSeconds = accessTtlSeconds;
        this.issuer = issuer;
    }

    @PostConstruct
    void init() {
        if (secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Integer userId, String username) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessTtlSeconds);

        return Jwts.builder()
                .issuer(issuer)
                .subject(String.valueOf(userId))
                .claim("username", username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public Claims parseAndValidate(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
