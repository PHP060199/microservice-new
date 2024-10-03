package com.example.identityservice.mapper;

import com.example.identityservice.domain.Permission;
import com.example.identityservice.dto.PermissionDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-03T11:10:49+0700",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public Permission toPermission(PermissionDTO permissionDTO) {
        if ( permissionDTO == null ) {
            return null;
        }

        Permission.PermissionBuilder permission = Permission.builder();

        permission.name( permissionDTO.getName() );
        permission.description( permissionDTO.getDescription() );

        return permission.build();
    }

    @Override
    public PermissionDTO toPermissionDTO(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionDTO.PermissionDTOBuilder permissionDTO = PermissionDTO.builder();

        permissionDTO.name( permission.getName() );
        permissionDTO.description( permission.getDescription() );

        return permissionDTO.build();
    }
}
