package com.shophouse.controller;

import com.shophouse.model.dto.auth.AdminCreationRequest;
import com.shophouse.model.dto.auth.JwtAuthenticationResponse;
import com.shophouse.model.dto.auth.LoginRequest;
import com.shophouse.model.dto.auth.UserRegistrationRequest;
import com.shophouse.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public JwtAuthenticationResponse registerUser(
            @Valid @RequestBody UserRegistrationRequest request) {

        log.info("User registration attempt for username: {}", request.getUsername());
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse authenticateUser(
            @Valid @RequestBody LoginRequest request) {

        log.info("Login attempt for username: {}", request.getUsername());
        return  authService.authenticateUser(request);
    }

    @PostMapping("/admin/create")
    public JwtAuthenticationResponse createAdmin(
            @Valid @RequestBody AdminCreationRequest request) {

        log.info("Admin creation attempt for username: {}", request.getUsername());
        return authService.createAdmin(request);
    }
}
