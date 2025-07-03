package com.example.fileservice.mapper;

import com.example.fileservice.domain.FileManagement;
import com.example.fileservice.dto.respone.FileInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileManagementMapper {
    @Mapping(target = "id", source = "fileName")
    FileManagement toFileManagement(FileInfo fileInfo);
}
