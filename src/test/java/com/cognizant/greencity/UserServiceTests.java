package com.cognizant.greencity;

import com.cognizant.greencity.dto.user.UserResponse;
import com.cognizant.greencity.dto.user.UserUpdateRequest;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.repository.UserRepository;
import com.cognizant.greencity.service.AuditLogService;
import com.cognizant.greencity.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuditLogService auditLogService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1);
        user.setEmail("test@example.com");
        user.setName("Test User");

        userResponse = new UserResponse();

    }

    @Test
    @DisplayName("Should list all users")
    void list_Success() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(modelMapper.map(any(User.class), eq(UserResponse.class))).thenReturn(userResponse);

        List<UserResponse> result = userService.list();

        assertFalse(result.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Should get user by ID")
    void get_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponse.class)).thenReturn(userResponse);

        UserResponse result = userService.get(1);

        assertNotNull(result);
        verify(userRepository).findById(1);
    }

    @Test
    @DisplayName("Should throw NotFoundException for invalid ID")
    void get_NotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.get(99));
    }

    @Test
    @DisplayName("Should update user and record audit log")
    void update_Success() {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setName("Updated Name");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(), eq(UserResponse.class))).thenReturn(userResponse);

        userService.update(1, request);

        verify(userRepository).save(user);
        verify(auditLogService).record(any(), eq("USER_UPDATE"), eq("users/1"));
    }

    @Test
    @DisplayName("Should delete user and record audit log")
    void delete_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.delete(1);

        verify(userRepository).delete(user);
        verify(auditLogService).record(user, "USER_DELETE", "users/1");
    }
}