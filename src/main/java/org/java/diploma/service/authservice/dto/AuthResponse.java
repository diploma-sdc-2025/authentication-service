package org.java.diploma.service.authservice.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
