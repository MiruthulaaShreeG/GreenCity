package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.auth.AuthResponse;
import com.cognizant.greencity.dto.auth.LoginRequest;
import com.cognizant.greencity.dto.auth.RegisterRequest;
import com.cognizant.greencity.service.AuthService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        logger.info("Received request to register new user");
        AuthResponse response = authService.register(request);
        logger.info("Successfully processed registration for user");
        return response;
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        logger.info("Received login request");
        AuthResponse response = authService.login(request);
        logger.info("Successfully processed login request");
        return response;
    }

    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        logger.info("Received request to fetch current user details");
        Map<String, Object> response = authService.getme(authentication);
        logger.info("Successfully fetched current user details");
        return response;
    }
}