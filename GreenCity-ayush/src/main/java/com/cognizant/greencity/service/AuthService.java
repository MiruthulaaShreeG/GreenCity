package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.auth.AuthResponse;
import com.cognizant.greencity.dto.auth.LoginRequest;
import com.cognizant.greencity.dto.auth.RegisterRequest;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.BadRequestException;
import com.cognizant.greencity.exception.UnauthorizedException;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.security.JwtService;
import com.cognizant.greencity.security.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private static final String DEFAULT_ROLE = "CITIZEN";
    private static final String DEFAULT_STATUS = "ACTIVE";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuditLogService auditLogService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            AuditLogService auditLogService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.auditLogService = auditLogService;
    }

    public AuthResponse register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setPhone(request.getPhone());
        user.setRole(DEFAULT_ROLE);
        user.setStatus(DEFAULT_STATUS);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        User saved = userRepository.save(user);
        auditLogService.record(saved, "REGISTER", "auth");

        String token = jwtService.generateToken(saved.getEmail(), Map.of(
                "uid", saved.getUserId(),
                "role", saved.getRole()
        ));

        return new AuthResponse(token, saved.getUserId(), saved.getName(), saved.getEmail(), saved.getRole());
    }

    public AuthResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail().trim().toLowerCase(), request.getPassword())
            );

            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
            User user = userRepository.findByEmail(principal.getUsername())
                    .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

            String token = jwtService.generateToken(user.getEmail(), Map.of(
                    "uid", user.getUserId(),
                    "role", user.getRole()
            ));

            auditLogService.record(user, "LOGIN", "auth");
            return new AuthResponse(token, user.getUserId(), user.getName(), user.getEmail(), user.getRole());
        } catch (AuthenticationException ex) {
            throw new UnauthorizedException("Invalid credentials");
        }
    }
}

