package com.example.identityservice.controller;



import com.example.identityservice.dto.PermissionDTO;
import com.example.identityservice.dto.respone.ApiResponse;
import com.example.identityservice.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionDTO> create(@RequestBody PermissionDTO permissionDTO) {
        return ApiResponse.<PermissionDTO>builder()
                .result(permissionService.createPermission(permissionDTO))
                .build();
    }

    @GetMapping("/getAllPermissions")
    ApiResponse<List<PermissionDTO>> getAll() {
        return ApiResponse.<List<PermissionDTO>>builder()
                .result(permissionService.getAllPermissions())
                .build();
    }

    @DeleteMapping
    ApiResponse<Void> delete(@RequestParam String permission) {
        permissionService.deletePermission(permission);
        return ApiResponse.<Void>builder().build();
    }
}
