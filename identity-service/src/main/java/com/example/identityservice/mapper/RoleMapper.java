package com.example.identityservice.mapper;


import com.example.identityservice.domain.Role;
import com.example.identityservice.dto.request.RoleRequest;
import com.example.identityservice.dto.respone.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
