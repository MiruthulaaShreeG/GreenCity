package com.cognizant.greencity;

import com.cognizant.greencity.dto.auth.AuthResponse;
import com.cognizant.greencity.dto.auth.LoginRequest;
import com.cognizant.greencity.dto.auth.RegisterRequest;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.BadRequestException;
import com.cognizant.greencity.exception.UnauthorizedException;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.security.JwtService;
import com.cognizant.greencity.security.UserPrincipal;
import com.cognizant.greencity.service.AuditLogService;
import com.cognizant.greencity.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setRole("CITIZEN");
    }

    @Test
    @DisplayName("Should register a new user successfully")
    void register_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Test User");
        request.setEmail("TEST@example.com ");
        request.setPassword("password123");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("test@example.com", user.getEmail());
        verify(userRepository).save(any(User.class));
        verify(auditLogService).record(any(), eq("REGISTER"), eq("auth"));
    }

    @Test
    @DisplayName("Should throw exception when registering with existing email")
    void register_EmailExists_ThrowsException() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> authService.register(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should login successfully and return JWT")
    void login_Success() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");


        Authentication authentication = mock(Authentication.class);
        UserPrincipal principal = mock(UserPrincipal.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        when(principal.getUsername()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(anyString(), anyMap())).thenReturn("mocked-jwt-token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getAccessToken());
        assertEquals(1, response.getUserId());
        verify(auditLogService).record(user, "LOGIN", "auth");
    }

    @Test
    @DisplayName("Should throw UnauthorizedException when credentials are wrong")
    void login_Failure_ThrowsUnauthorized() {
        LoginRequest request = new LoginRequest();
        request.setEmail("wrong@example.com");
        request.setPassword("wrongpass");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(UnauthorizedException.class, () -> authService.login(request));
    }
}