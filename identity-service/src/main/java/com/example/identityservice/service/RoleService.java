package com.example.identityservice.service;


import com.example.common.exception.CustomException;
import com.example.common.exception.define.ErrorCode;
import com.example.common.exception.define.ErrorMessage;
import com.example.identityservice.domain.Permission;
import com.example.identityservice.domain.Role;
import com.example.identityservice.dto.request.RoleRequest;
import com.example.identityservice.dto.respone.RoleResponse;
import com.example.identityservice.mapper.RoleMapper;
import com.example.identityservice.repository.PermissionRepository;
import com.example.identityservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    private final PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request) {
        if (roleRepository.existsById(request.getName())) {
            throw new CustomException(ErrorMessage.ROLE_ALREADY_EXISTS, ErrorCode.exist);
        }

        Role role = roleMapper.toRole(request);

        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String role) {
        roleRepository.findById(role)
                .orElseThrow(()-> new CustomException(ErrorMessage.ROLE_NOT_FOUND, ErrorCode.notFound));
        roleRepository.deleteById(role);
    }
}
