package com.math713.bankapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Data required to create user")
public record CreateUserRequest(
        @Schema(description = "User name", example = "João Pedro")
        @NotBlank(message = "name is required")
        String name,

        @Schema(description = "User email", example = "joao@email.com")
        @NotBlank(message = "email is required")
        @Email(message = "Email must be valid")
        String email
) {}