package com.cognizant.greencity.controller;

import com.cognizant.greencity.dto.user.UserResponse;
import com.cognizant.greencity.dto.user.UserUpdateRequest;
import com.cognizant.greencity.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> list() {
        log.info("Received request to list users");
        List<UserResponse> response = userService.list();
        log.info("Successfully fetched users");
        return response;
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Integer id) {
        log.info("Received request to get user id: {}", id);
        UserResponse response = userService.get(id);
        log.info("Successfully fetched user id: {}", id);
        return response;
    }



    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Integer id, @Valid @RequestBody UserUpdateRequest request) {
        log.info("Received request to update user id: {}", id);
        UserResponse response = userService.update(id, request);
        log.info("Successfully updated user id: {}", id);
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        log.info("Received request to delete user id: {}", id);
        userService.delete(id);
        log.info("Successfully deleted user id: {}", id);
    }
}