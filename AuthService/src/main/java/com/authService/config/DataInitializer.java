package com.authService.config;

import com.authService.entity.UserEntity;
import com.authService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.authService.entity.RoleType;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) return;

        List<UserEntity> users = List.of(
                new UserEntity(null, "admin", encoder.encode("1234"), "pmikulko2@gmail.com", "Admin", "Adminov", RoleType.ADMIN),
                new UserEntity(null, "moder", encoder.encode("1234"), "mod@gmail.com", "Mod", "User", RoleType.USER),
                new UserEntity(null, "test", encoder.encode("1234"), "test@gmail.com", "Test", "User", RoleType.USER)
        );

        repository.saveAll(users);
    }
}