package org.java.diploma.service.authservice.repository;

import org.java.diploma.service.authservice.entity.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.java.diploma.service.authservice.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class RefreshTokenRepositoryTest {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void findAllByUserId() {
        User user = new User();
        user.setEmail("a@a.com");
        user.setUsername("user");
        user.setPasswordHash("hash");

        userRepository.save(user);

        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setTokenHash("hash");
        rt.setExpiresAt(Instant.now().plusSeconds(60));

        refreshTokenRepository.save(rt);

        assertEquals(1,
                refreshTokenRepository.findAllByUser_Id(user.getId()).size());
    }
}
