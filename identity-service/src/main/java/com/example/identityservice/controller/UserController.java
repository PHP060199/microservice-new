package com.example.identityservice.controller;

import com.example.identityservice.dto.UserDTO;
import com.example.identityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getById")
    public UserDTO getUserById(@RequestParam Long  userId){
        return userService.getUserById(userId);
    }

    @PutMapping()
    public UserDTO updateUser(@RequestParam Long userId, @RequestBody UserDTO userDTO){
        return userService.updateUser(userId, userDTO);
    }

    @DeleteMapping()
    String deleteUser(@RequestParam Long userId){
        userService.deleteUser(userId);
        return "User has been deleted";
    }
}
