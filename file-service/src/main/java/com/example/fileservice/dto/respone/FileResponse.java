package com.example.fileservice.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileResponse {
    String originalFilename;
    String url;
}
