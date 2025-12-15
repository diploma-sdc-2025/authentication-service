package org.java.diploma.service.authservice.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String identifier, // email or username
        @NotBlank String password,
        String deviceInfo
) {}
