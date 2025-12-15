package org.java.diploma.service.authservice.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public final class TokenUtil {
    private static final SecureRandom RNG = new SecureRandom();

    private TokenUtil() {}

    public static String newOpaqueToken() {
        byte[] bytes = new byte[48];
        RNG.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public static String sha256B64Url(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] dig = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(dig);
        } catch (Exception e) {
            throw new IllegalStateException("Hash failure", e);
        }
    }
}

