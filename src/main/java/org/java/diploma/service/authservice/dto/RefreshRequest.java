package org.java.diploma.service.authservice.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(
        @NotBlank String refreshToken,
        String deviceInfo
) {}
