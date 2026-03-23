package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.user.UserCreateRequest;
import com.cognizant.greencity.dto.user.UserResponse;
import com.cognizant.greencity.dto.user.UserUpdateRequest;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.BadRequestException;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String DEFAULT_ROLE = "CITIZEN";
    private static final String DEFAULT_STATUS = "ACTIVE";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditLogService auditLogService;
    private final ModelMapper modelMapper;

    public List<UserResponse> list() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UserResponse get(Integer id) {
        return toResponse(getEntity(id));
    }

    public UserResponse create(UserCreateRequest request) {
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
        auditLogService.record(saved, "USER_CREATE", "users");
        return toResponse(saved);
    }

    public UserResponse update(Integer id, UserUpdateRequest request) {
        User user = getEntity(id);

        if (request.getName() != null) user.setName(request.getName().trim());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getStatus() != null) user.setStatus(request.getStatus());

        User saved = userRepository.save(user);
        auditLogService.record(saved, "USER_UPDATE", "users/" + id);
        return toResponse(saved);
    }

    public void delete(Integer id) {
        User user = getEntity(id);
        userRepository.delete(user);
        auditLogService.record(user, "USER_DELETE", "users/" + id);
    }

    private User getEntity(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    private UserResponse toResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
}

