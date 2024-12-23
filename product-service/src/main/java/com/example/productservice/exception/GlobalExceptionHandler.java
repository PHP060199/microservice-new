package com.example.productservice.exception;

import com.example.productservice.dto.ApiResponse;
import com.example.productservice.exception.define.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    ResponseEntity<ApiResponse> handlingAppException(CustomException exception){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(exception.getCode());
        apiResponse.setMessage(exception.getMessage());

        return ResponseEntity
                .status(exception.getCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){
        String message = exception.getFieldError().getDefaultMessage();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.invalid);
        apiResponse.setMessage(message);

        return ResponseEntity
                .status(ErrorCode.invalid)
                .body(apiResponse);
    }
}
