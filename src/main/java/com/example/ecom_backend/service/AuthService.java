package com.example.ecom_backend.service;

import com.example.ecom_backend.dto.request.LoginRequest;
import com.example.ecom_backend.dto.request.RegisterRequest;
import com.example.ecom_backend.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
