package com.cognizant.greencity.service.impl;

import com.cognizant.greencity.dao.UserRepository;
import com.cognizant.greencity.dto.UserDTO;
import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO registerUser(UserDTO userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setRole(userDto.getRole());
        user.setPasswordHash(userDto.getPassword());
        user.setStatus("ACTIVE");

        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }
    @Override
    public UserDTO updateUser(Long id, UserDTO userDto) {

        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isPresent()) {
            User user = existingUserOpt.get();

            user.setName(userDto.getName());
            user.setPhone(userDto.getPhone());
            user.setRole(userDto.getRole());
            user.setStatus(userDto.getStatus());

            User updatedUser = userRepository.save(user);
            return mapToDTO(updatedUser);
        }
        return null;
    }
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }
    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return (user != null) ? mapToDTO(user) : null;
    }
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> dtos = new ArrayList<>();
        for (User u : users) {
            dtos.add(mapToDTO(u));
        }
        return dtos;
    }
    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        return dto;
    }
}
