package com.authService.dto.event;

public record UserEvent(
        EventType type,
        String email,
        String username
) {}