package org.java.diploma.service.authservice.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(
        classes = JwtService.class,
        properties = {
                "auth.jwt.secret=12345678901234567890123456789012",
                "spring.flyway.enabled=false",
                "spring.jpa.hibernate.ddl-auto=none"
        }
)
class JwtServiceTest {

    @Autowired
    JwtService jwtService;

    @Test
    void createAndParseToken() {
        String token = jwtService.createAccessToken(1, "user");

        Claims claims = jwtService.parseAndValidate(token);

        assertEquals("1", claims.getSubject());
        assertEquals("user", claims.get("username"));
    }
}

