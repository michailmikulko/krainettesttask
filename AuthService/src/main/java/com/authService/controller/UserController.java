package com.authService.controller;

import com.authService.dto.request.CreateUserRequest;
import com.authService.dto.request.UpdateMeRequest;
import com.authService.dto.request.UpdateUserRequest;
import com.authService.dto.response.UserResponse;
import com.authService.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(
            @RequestBody @Valid CreateUserRequest request
    ) {
        return userService.createUser(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserById(
            @PathVariable("id") Long id
    ) {
        return userService.getUserById(id);
    }
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getMe(
            Authentication authentication
    ) {
        String email = authentication.getName();
        return userService.getMe(email);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers() {
        return userService.findAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUser(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateUserRequest request
    ) {
        return userService.updateUser(id, request);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateMe(
            Authentication authentication,
            @RequestBody @Valid UpdateMeRequest request
    ) {
        String email = authentication.getName();
        return userService.updateMe(email, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(
            @PathVariable("id") Long id
    ) {
        userService.deleteUser(id);
    }
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMe(
            Authentication authentication
    ) {
        String email = authentication.getName();
        userService.deleteMe(email);
    }

}
