package com.example.identityservice.service;

import com.example.identityservice.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long userId);


}
