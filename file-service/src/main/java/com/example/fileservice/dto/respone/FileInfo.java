package com.example.fileservice.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileInfo {
    String fileName;
    String contentType;
    long fileSize;
    String md5Checksum;
    String path;
    String url;
}
