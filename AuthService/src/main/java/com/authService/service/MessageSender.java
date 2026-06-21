package com.authService.service;


import com.authService.dto.event.UserEvent;
import lombok.Setter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Setter
@Service
public class MessageSender {
    @Value("${queue.name}")
    private String queueName;
    private AmqpTemplate amqpTemplate;
    public void send(UserEvent event) {
        amqpTemplate.convertAndSend(queueName, event);
    }

}
