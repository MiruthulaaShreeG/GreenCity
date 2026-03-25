package com.cognizant.greencity.service;

import com.cognizant.greencity.dto.user.UserResponse;
import com.cognizant.greencity.dto.user.UserUpdateRequest;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.exception.NotFoundException;
import com.cognizant.greencity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuditLogService auditLogService;
    private final ModelMapper modelMapper;

    public List<UserResponse> list() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UserResponse get(Integer id) {
        return toResponse(getEntity(id));
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

