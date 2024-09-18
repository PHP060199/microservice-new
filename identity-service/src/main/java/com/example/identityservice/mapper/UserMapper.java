package com.example.identityservice.mapper;


import com.example.identityservice.domain.User;
import com.example.identityservice.dto.UserDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDTO request);

    UserDTO toUserDTO(User user);
}
