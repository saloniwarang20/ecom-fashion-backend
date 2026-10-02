package com.example.ecom_backend.service;

import com.example.ecom_backend.dto.request.UserRequest;
import com.example.ecom_backend.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse getCurrentUser();

    UserResponse updateProfile(UserRequest request);

    List<UserResponse> getAllUsers();

    void deleteUser(Long id);
}
