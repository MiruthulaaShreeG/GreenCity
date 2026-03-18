package com.cognizant.greencity.service;
import com.cognizant.greencity.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDto);
    UserDTO getUserByEmail(String email);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDto);
    void deleteUser(Long id);
}
