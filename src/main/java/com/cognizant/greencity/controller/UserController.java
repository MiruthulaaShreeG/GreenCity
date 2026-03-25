package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.user.UserResponse;
import com.cognizant.greencity.dto.user.UserUpdateRequest;
import com.cognizant.greencity.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> list() {
        logger.info("Received request to list users");
        List<UserResponse> response = userService.list();
        logger.info("Successfully fetched users");
        return response;
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Integer id) {
        logger.info("Received request to get user id: {}", id);
        UserResponse response = userService.get(id);
        logger.info("Successfully fetched user id: {}", id);
        return response;
    }

//    @PostMapping
//    public UserResponse create(@Valid @RequestBody UserCreateRequest request) {
//        return userService.create(request);
//    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Integer id, @Valid @RequestBody UserUpdateRequest request) {
        logger.info("Received request to update user id: {}", id);
        UserResponse response = userService.update(id, request);
        logger.info("Successfully updated user id: {}", id);
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        logger.info("Received request to delete user id: {}", id);
        userService.delete(id);
        logger.info("Successfully deleted user id: {}", id);
    }
}