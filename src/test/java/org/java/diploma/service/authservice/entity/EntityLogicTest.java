package org.java.diploma.service.authservice.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.Instant;

class EntityLogicTest {

    @Test
    void refreshToken_isActive() {
        RefreshToken rt = new RefreshToken();
        rt.setExpiresAt(Instant.now().plusSeconds(60));

        assertTrue(rt.isActive(Instant.now()));
    }

    @Test
    void passwordResetToken_notUsableWhenExpired() {
        PasswordResetToken prt = new PasswordResetToken();
        prt.setExpiresAt(Instant.now().minusSeconds(10));

        assertFalse(prt.isUsable(Instant.now()));
    }
}

