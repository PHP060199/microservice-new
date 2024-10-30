package com.example.event.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationEvent {
    String channel;
    String recipient;
    String subject;
    String body;
}
