package com.example.identityservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntrospectRequest {
    private String token;
}