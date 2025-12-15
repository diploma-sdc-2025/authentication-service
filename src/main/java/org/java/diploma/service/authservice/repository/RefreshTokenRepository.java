package org.java.diploma.service.authservice.repository;

import org.java.diploma.service.authservice.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByTokenHash(String tokenHash);
    List<RefreshToken> findAllByUser_Id(Integer userId);
    long deleteByExpiresAtBefore(Instant cutoff);
}
