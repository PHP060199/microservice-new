package com.example.identityservice.service;

import com.example.event.dto.NotificationEvent;
import com.example.identityservice.domain.User;
import com.example.identityservice.dto.PageResponse;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.respone.UserResponse;
import com.example.identityservice.exception.CustomException;
import com.example.identityservice.exception.define.ErrorCode;
import com.example.identityservice.exception.define.ErrorMessage;
import com.example.identityservice.mapper.UserMapper;
import com.example.identityservice.repository.RoleRepository;
import com.example.identityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    RoleRepository roleRepository;

    KafkaTemplate<String, Object> kafkaTemplate;



    public UserResponse createUser(UserCreationRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(ErrorMessage.USERNAME_ALREADY_EXISTS, ErrorCode.exist);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel("EMAIL")
                .recipient(user.getEmail())
                .subject("Welcome to identity service")
                .body("Hello, " + user.getUsername() + "!")
                .build();

        //Publish message with kafka
        kafkaTemplate.send("notification-delivery", notificationEvent);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND, ErrorCode.notFound));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }


    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND, ErrorCode.notFound));
        userRepository.deleteById(userId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<UserResponse> getAllUsers(int page, int size) {

        Sort sort = Sort.by("username").ascending();
        Pageable pageable = PageRequest.of(page -1, size, sort);

        var pageData = userRepository.findAll(pageable);

        return PageResponse.<UserResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(userMapper::toUserResponse).toList())
                .build();

    }


    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorMessage.USER_NOT_FOUND, ErrorCode.notFound));
        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new CustomException(ErrorMessage.USER_NOT_FOUND, ErrorCode.notFound));

        return userMapper.toUserResponse(user);
    }

}
