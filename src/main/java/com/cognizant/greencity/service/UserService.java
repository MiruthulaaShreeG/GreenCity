package com.cognizant.greencity.service;
import com.cognizant.greencity.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDto);
    UserDTO getUserByEmail(String email);
    UserDTO getUserById(Integer id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Integer id, UserDTO userDto);
    void deleteUser(Integer id);
}
