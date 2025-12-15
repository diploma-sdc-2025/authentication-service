package org.java.diploma.service.authservice.repository;

import org.java.diploma.service.authservice.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);
    long deleteByExpiresAtBefore(Instant cutoff);
}
