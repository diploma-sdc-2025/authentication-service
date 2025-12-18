package org.java.diploma.service.authservice.repository;

import org.java.diploma.service.authservice.entity.PasswordResetToken;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.java.diploma.service.authservice.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class PasswordResetTokenRepositoryTest {

    @Autowired
    PasswordResetTokenRepository repository;

    @Autowired
    UserRepository userRepository;

    @Test
    void deleteExpiredTokens() {
        User user = new User();
        user.setEmail("a@a.com");
        user.setUsername("user");
        user.setPasswordHash("hash");

        userRepository.save(user);

        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setTokenHash("hash");
        token.setExpiresAt(Instant.now().minusSeconds(60));

        repository.save(token);

        long deleted =
                repository.deleteByExpiresAtBefore(Instant.now());

        assertEquals(1, deleted);
    }
}

