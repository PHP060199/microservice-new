package com.example.identityservice.dto;

import com.example.identityservice.exception.define.ErrorMessage;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @Size(min = 3,message = "Username must be at least 3 characters.")
    String username;

    @Size(min = 8, message = "Password must be at least 8 characters.")
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
}
