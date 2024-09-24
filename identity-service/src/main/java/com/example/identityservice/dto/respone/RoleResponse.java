package com.example.identityservice.dto.respone;

import com.example.identityservice.dto.PermissionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    private String name;
    private String description;
    Set<PermissionDTO> permissions;
}
