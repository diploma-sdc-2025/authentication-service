package org.java.diploma.service.authservice.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TokenUtilTest {

    @Test
    void newOpaqueToken_unique() {
        String t1 = TokenUtil.newOpaqueToken();
        String t2 = TokenUtil.newOpaqueToken();

        assertNotEquals(t1, t2);
    }

    @Test
    void sha256B64Url_consistent() {
        String hash1 = TokenUtil.sha256B64Url("test");
        String hash2 = TokenUtil.sha256B64Url("test");

        assertEquals(hash1, hash2);
    }
}

