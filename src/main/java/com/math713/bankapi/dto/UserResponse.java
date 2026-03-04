package com.math713.bankapi.dto;

public record UserResponse(
        Long id,
        String name,
        String email
) {}