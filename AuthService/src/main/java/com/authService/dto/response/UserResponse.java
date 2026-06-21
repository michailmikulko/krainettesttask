package com.authService.dto.response;

import com.authService.entity.RoleType;

public record UserResponse(
        String username,
        String email,
        String firstName,
        String lastName,
        RoleType role
) {}