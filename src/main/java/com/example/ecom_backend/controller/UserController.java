package com.example.ecom_backend.controller;

import com.example.ecom_backend.dto.request.UserRequest;
import com.example.ecom_backend.dto.response.UserResponse;
import com.example.ecom_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse getCurrentUser(){
        return userService.getCurrentUser();
    }

    @PutMapping("/me")
    public UserResponse updateProfile(@RequestBody UserRequest request){
        return userService.updateProfile(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
