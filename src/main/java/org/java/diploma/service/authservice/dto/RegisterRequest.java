package org.java.diploma.service.authservice.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @Email @NotBlank String email,
        @NotBlank @Size(min = 3, max = 50) String username,
        @NotBlank @Size(min = 8, max = 72) String password
) {}
