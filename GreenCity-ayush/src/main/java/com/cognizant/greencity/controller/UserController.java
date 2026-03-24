package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.user.UserCreateRequest;
import com.cognizant.greencity.dto.user.UserResponse;
import com.cognizant.greencity.dto.user.UserUpdateRequest;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> list() {
        return userService.list().stream().map(UserController::toResponse).toList();
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Integer id) {
        return toResponse(userService.get(id));
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody UserCreateRequest request) {
        return toResponse(userService.create(request));
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Integer id, @Valid @RequestBody UserUpdateRequest request) {
        return toResponse(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

    private static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getStatus()
        );
    }
}

