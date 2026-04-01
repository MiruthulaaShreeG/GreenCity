package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.auth.AuthResponse;
import com.cognizant.greencity.dto.auth.LoginRequest;
import com.cognizant.greencity.dto.auth.RegisterRequest;
import com.cognizant.greencity.service.AuthService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        log.info("Received request to register new user");
        AuthResponse response = authService.register(request);
        log.info("Successfully processed registration for user");
        return response;
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        log.info("Received login request");
        AuthResponse response = authService.login(request);
        log.info("Successfully processed login request");
        return response;
    }

    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        log.info("Received request to fetch current user details");
        Map<String, Object> response = authService.getme(authentication);
        log.info("Successfully fetched current user details");
        return response;
    }
}