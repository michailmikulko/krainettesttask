package com.authService.dto.request;

import com.authService.entity.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(

        String username,

        String password,

        @Email
        String email,

        String firstName,

        String lastName,

        RoleType role
) {
}