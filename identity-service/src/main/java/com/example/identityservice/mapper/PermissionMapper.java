package com.example.identityservice.mapper;


import com.example.identityservice.domain.Permission;
import com.example.identityservice.dto.PermissionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionDTO permissionDTO);

    PermissionDTO toPermissionDTO(Permission permission);
}
