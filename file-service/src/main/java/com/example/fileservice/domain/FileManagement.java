package com.example.fileservice.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "file_management")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileManagement {
    @MongoId
    String id;
    String ownerId;
    String contentType;
    long size;
    String md5Checksum;
    String pass;

}
