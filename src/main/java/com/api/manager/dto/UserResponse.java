package com.api.manager.dto;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String role
) {
}
