package com.authService.service;

import com.authService.dto.event.EventType;
import com.authService.dto.event.UserEvent;

import com.authService.mapping.UserMapper;
import com.authService.dto.request.CreateUserRequest;
import com.authService.dto.request.UpdateMeRequest;
import com.authService.dto.request.UpdateUserRequest;
import com.authService.dto.response.UserResponse;
import com.authService.entity.RoleType;
import com.authService.entity.UserEntity;
import com.authService.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final MessageSender messageSender;

    private final UserRepository repository;
    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;


    public UserResponse getUserById(Long id) {
        log.info("Called getUserById id={}", id);
        UserEntity userEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found user by id = " + id));
        return mapper.toResponse(userEntity);

    }

    public List<UserResponse> findAllUsers() {
        log.info("Called findAllUsers");
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Called createUser");

        UserEntity entity = mapper.toEntity(request);

        entity.setPassword(passwordEncoder.encode(request.password()));
        entity.setRole(RoleType.USER);

        UserEntity saved = repository.save(entity);

        sendMessage(EventType.USER_CREATED, saved.getEmail(), saved.getUsername());

        return mapper.toResponse(saved);
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        log.info("Called updateUser id = {}", id);

        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        mapper.updateEntity(request, user);

        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }

        UserEntity saved = repository.save(user);

        return mapper.toResponse(saved);
    }
    @Transactional
    public void deleteUser(Long id) {
        log.info("Called deleteUser id = {}", id);

        UserEntity userToDelete = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found user by id = " + id));
        sendMessage(EventType.USER_DELETED, userToDelete.getEmail(), userToDelete.getUsername());

        repository.deleteById(id);
    }
    @Transactional
    public UserResponse updateMe(String email, UpdateMeRequest request) {
        log.info("Called updateMe");

        UserEntity user = repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        mapper.updateMeEntity(request, user);
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        sendMessage(EventType.USER_UPDATED, request.email(), request.username());

        return mapper.toResponse(repository.save(user));
    }
    private void sendMessage(EventType eventType, String email, String username) {
        log.info("Called sendMessage");
        UserEvent event = new UserEvent(
                eventType,
                email,
                username
        );
        messageSender.send(event);
    }

}
