package com.example.identityservice.service;


import com.example.common.exception.CustomException;
import com.example.common.exception.define.ErrorCode;
import com.example.common.exception.define.ErrorMessage;
import com.example.identityservice.domain.Permission;
import com.example.identityservice.dto.PermissionDTO;
import com.example.identityservice.mapper.PermissionMapper;
import com.example.identityservice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    private final PermissionMapper permissionMapper;

    public PermissionDTO createPermission(PermissionDTO permissionDTO) {
        if (permissionRepository.existsById(permissionDTO.getName())) {
            throw new CustomException(ErrorMessage.PERMISSION_ALREADY_EXISTS, ErrorCode.exist);
        }
        Permission permission = permissionMapper.toPermission(permissionDTO);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionDTO(permission);
    }

    public List<PermissionDTO> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionDTO).toList();
    }

    public void deletePermission(String permission) {
        permissionRepository.findById(permission)
                .orElseThrow(()-> new CustomException(ErrorMessage.PERMISSION_NOT_FOUND, ErrorCode.notFound));
        permissionRepository.deleteById(permission);
    }
}
