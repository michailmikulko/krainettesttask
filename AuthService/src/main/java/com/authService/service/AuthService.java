package com.authService.service;

import com.authService.dto.jwt.JwtAuthenticationDto;
import com.authService.dto.jwt.RefreshTokenDto;
import com.authService.dto.jwt.UserCredentialsDto;
import com.authService.entity.UserEntity;
import com.authService.repository.UserRepository;
import com.authService.sequrity.jwt.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    @SneakyThrows
    public JwtAuthenticationDto signIn(UserCredentialsDto dto){
        log.info("Called signIn");
        UserEntity userEntity = findByCredentials(dto);
        return jwtService.generateAuthToken(userEntity.getEmail());
    }
    @SneakyThrows
    public JwtAuthenticationDto refreshToken(RefreshTokenDto dto){
        log.info("Called refreshToken");

        String refreshToken = dto.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            UserEntity userEntity;
            try {
                userEntity = findByEmail(jwtService.getEmailFromToken(refreshToken));
            } catch (Exception e) {
                throw new EntityNotFoundException(e);
            }
            return jwtService.refreshBaseToken(userEntity.getEmail(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
    }
    @SneakyThrows
    private UserEntity findByCredentials(UserCredentialsDto userCredentialsDto){
        log.info("Called findByCredentials");

        Optional<UserEntity> optionalUser = repository.findByEmail(userCredentialsDto.getEmail());
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Email or password is not correct");
    }
    @SneakyThrows
    private UserEntity findByEmail(String email){
        return repository.findByEmail(email).orElseThrow(() -> new Exception(String.format("User with email %s not found", email)));
    }

}
