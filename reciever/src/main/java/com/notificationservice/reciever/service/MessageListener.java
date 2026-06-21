package com.notificationservice.reciever.service;

import com.notificationservice.reciever.dto.UserEvent;
import com.notificationservice.reciever.entity.RoleType;
import com.notificationservice.reciever.entity.UserEntity;
import com.notificationservice.reciever.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessageListener {
    private final MailSenderService mailSender;
    private final UserRepository userRepository;

    @RabbitListener(queues = "${queue.name}")
    public void receive(UserEvent message) {

        log.info("Got event {}", message.type());
        List<UserEntity> admins = userRepository.findAllByRole(RoleType.ADMIN);

        List<String> emails = admins.stream()
                .map(UserEntity::getEmail)
                .toList();

        String action = switch (message.type()) {
            case USER_CREATED -> "Создан";
            case USER_UPDATED -> "Изменен";
            case USER_DELETED -> "Удален";
        };

        String subject = action + " пользователь " + message.username();

        String body = action + " пользователь с именем - " + message.username()
                + " и почтой - " + message.email();

        mailSender.sendToAll(emails, subject, body);
    }

}