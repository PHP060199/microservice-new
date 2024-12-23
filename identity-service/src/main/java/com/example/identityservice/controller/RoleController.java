package com.example.identityservice.controller;



import com.example.identityservice.dto.request.RoleRequest;
import com.example.identityservice.dto.respone.ApiResponse;
import com.example.identityservice.dto.respone.RoleResponse;
import com.example.identityservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping("/getAllRoles")
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping
    ApiResponse<Void> delete(@RequestParam String role) {
        roleService.delete(role);
        return ApiResponse.<Void>builder().build();
    }
}
