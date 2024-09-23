package com.example.identityservice.service.impl;

import com.example.identityservice.domain.User;
import com.example.identityservice.dto.UserDTO;
import com.example.identityservice.exception.CustomException;
import com.example.identityservice.exception.define.ErrorCode;
import com.example.identityservice.exception.define.ErrorMessage;
import com.example.identityservice.mapper.UserMapper;
import com.example.identityservice.repository.UserRepository;
import com.example.identityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new CustomException(ErrorMessage.USERNAME_ALREADY_EXISTS, ErrorCode.exist);
        }

        User user = User.builder()
                .username(userDTO.getUsername())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .dob(userDTO.getDob())
                .build();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND, ErrorCode.notFound));

        User updateUser = User.builder()
                .id(userId)
                .username(userDTO.getUsername())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .dob(userDTO.getDob())
                .build();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        updateUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(updateUser);
        userRepository.save(updateUser);
        return userMapper.toUserDTO(updateUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND, ErrorCode.notFound));
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserDTO).toList();
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorMessage.USER_NOT_FOUND, ErrorCode.notFound));
        return userMapper.toUserDTO(user);
    }
}
