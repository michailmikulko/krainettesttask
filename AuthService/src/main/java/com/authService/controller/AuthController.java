package com.authService.controller;

import com.authService.dto.jwt.JwtAuthenticationDto;
import com.authService.dto.jwt.RefreshTokenDto;
import com.authService.dto.jwt.UserCredentialsDto;
import com.authService.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @SneakyThrows
    @PostMapping("/sign-in")
    public JwtAuthenticationDto signIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        return authService.signIn(userCredentialsDto);
    }

    @SneakyThrows
    @PostMapping("/refresh")
    public JwtAuthenticationDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        return authService.refreshToken(refreshTokenDto);
    }
}