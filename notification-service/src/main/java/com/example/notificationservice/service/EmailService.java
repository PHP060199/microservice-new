package com.example.notificationservice.service;


import com.example.common.exception.CustomException;
import com.example.common.exception.define.ErrorCode;
import com.example.common.exception.define.ErrorMessage;
import com.example.notificationservice.client.EmailClient;
import com.example.notificationservice.dto.request.EmailRequest;
import com.example.notificationservice.dto.request.SendEmailRequest;
import com.example.notificationservice.dto.request.Sender;
import com.example.notificationservice.dto.respone.EmailResponse;
import feign.FeignException;
import lombok.AccessLevel;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class EmailService {
    EmailClient emailClient;

    @NonFinal
    @Value("${notification.email.brevo-apikey}")
    String apiKey;

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("PHP")
                        .email("hoaiphat990106@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException e) {
            throw new CustomException(ErrorMessage.CANNOT_SEND_EMAIL, ErrorCode.missing);
        }

    }
}
