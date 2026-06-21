package com.notificationservice.reciever.dto;


public record UserEvent(
        EventType type,
        String email,
        String username
) {
}