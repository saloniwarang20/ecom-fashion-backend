package com.example.ecom_backend.service.impl;

import com.example.ecom_backend.dto.request.LoginRequest;
import com.example.ecom_backend.dto.request.RegisterRequest;
import com.example.ecom_backend.dto.response.AuthResponse;
import com.example.ecom_backend.entity.User;
import com.example.ecom_backend.enums.Role;
import com.example.ecom_backend.repository.UserRepository;
import com.example.ecom_backend.security.CustomerUserDetails;
import com.example.ecom_backend.security.JwtService;
import com.example.ecom_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new RuntimeException("Phone Number already exists");
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        //encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setPhoneNumber(request.getPhoneNumber());

        //every registered user is USER
        user.setRole(Role.USER);

        user.setEnabled(true);

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(
                new CustomerUserDetails(savedUser)
        );

        return new AuthResponse(token,savedUser.getRole());
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));

        String token = jwtService.generateToken(
                new CustomerUserDetails(user)
        );

        return new AuthResponse(token,user.getRole());
    }
}
