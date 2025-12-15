package com.ceac.gametracker.auth.dto;

public record AuthResponse(
        Long userId,
        String username,
        String role
) {}
