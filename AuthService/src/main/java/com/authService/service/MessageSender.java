package com.authService.service;


import com.authService.dto.event.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSender {

    private final AmqpTemplate amqpTemplate;

    @Value("${queue.name}")
    private String queueName;

    public void send(UserEvent event) {
        amqpTemplate.convertAndSend(queueName, event);
    }
}