package com.math713.bankapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

@Schema(description = "Data required to update a user")
public record UpdateUserRequest(
        @Schema(description = "User name", example = "João Pedro")
        String name,

        @Schema(description = "User email", example = "joao@email.com")
        @Email(message = "Email must be valid")
        String email
) {}