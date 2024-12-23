package com.example.identityservice.controller;


import com.example.identityservice.dto.PageResponse;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.respone.ApiResponse;
import com.example.identityservice.dto.respone.UserResponse;
import com.example.identityservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
       return ApiResponse.<UserResponse>builder()
               .result(userService.createUser(request)).
               build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResponse<UserResponse>> getAllUsers(
            @RequestParam(required = false, value = "page", defaultValue = "1") int page,
            @RequestParam(required = false, value = "size", defaultValue = "10") int size
            ){
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .result(userService.getAllUsers(page, size))
                .build();
    }

    @GetMapping("/getById")
    public ApiResponse<UserResponse> getUserById(@RequestParam Long  userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }

    @GetMapping("/getMyInfo")
    public ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping()
    public ApiResponse<UserResponse> updateUser(@RequestParam Long userId, @RequestBody @Valid UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping()
    public ApiResponse<String> deleteUser(@RequestParam Long userId){
        userService.deleteUser(userId);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult("User has been deleted");
        return apiResponse;
    }
}
