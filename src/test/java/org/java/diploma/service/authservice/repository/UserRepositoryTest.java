package org.java.diploma.service.authservice.repository;

import org.java.diploma.service.authservice.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByEmailIgnoreCase() {
        User user = new User();
        user.setEmail("TEST@MAIL.COM");
        user.setUsername("user");
        user.setPasswordHash("hash");

        userRepository.save(user);

        assertTrue(
                userRepository.findByEmailIgnoreCase("test@mail.com").isPresent()
        );
    }

    @Test
    void findByUsernameIgnoreCase() {
        User user = new User();
        user.setEmail("a@a.com");
        user.setUsername("UserName");
        user.setPasswordHash("hash");

        userRepository.save(user);

        assertTrue(
                userRepository.findByUsernameIgnoreCase("username").isPresent()
        );
    }
}

