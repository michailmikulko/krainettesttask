package com.authService.dto.request;

public record AuthRequest(
        String username,
        String password
) {}