package com.example.fileservice.service;

import com.example.fileservice.domain.FileManagement;
import com.example.fileservice.dto.respone.FileInfo;
import com.example.fileservice.dto.respone.FileResponse;
import com.example.fileservice.mapper.FileManagementMapper;
import com.example.fileservice.repository.FileManagementRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileService {

    @NonFinal
    @Value("${app.file.storage-dir}")
    String storageDir;

    @NonFinal
    @Value("${app.file.download-prefix}")
    String downloadPrefix;

    FileManagementRepository fileManagementRepository;
    FileManagementMapper fileManagementMapper;

    public FileResponse uploadFile(MultipartFile file) throws IOException {

        FileInfo fileInfo = store(file);

        FileManagement fileManagement = fileManagementMapper.toFileManagement(fileInfo);

        fileManagementRepository.save(fileManagement);

        return FileResponse.builder()
                .originalFilename(file.getOriginalFilename())
                .url(fileInfo.getUrl())
                .build();
    }




    public FileInfo store(MultipartFile file) throws IOException {
        Path folder = Paths.get(storageDir);
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String fileName = Objects.isNull(fileExtension)
                ? UUID.randomUUID().toString()
                : UUID.randomUUID() + "."
                + StringUtils.getFilenameExtension(file.getOriginalFilename());

        Path filePath = folder.resolve(fileName).normalize().toAbsolutePath();
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return FileInfo.builder()
                .fileName(fileName)
                .fileSize(file.getSize())
                .contentType(file.getContentType())
                .md5Checksum(DigestUtils.md5DigestAsHex(file.getInputStream()))
                .path(filePath.toString())
                .url(downloadPrefix + fileName)
                .build();
    }

}
