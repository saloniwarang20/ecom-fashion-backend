package com.example.ecom_backend.service.impl;

import com.example.ecom_backend.dto.request.UserRequest;
import com.example.ecom_backend.dto.response.UserResponse;
import com.example.ecom_backend.entity.User;
import com.example.ecom_backend.mapper.UserMapper;
import com.example.ecom_backend.repository.UserRepository;
import com.example.ecom_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private User getAuthenticateUser(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
    }

    @Override
    public UserResponse getCurrentUser() {

        User user = getAuthenticateUser();

        return userMapper.convertToResponse(user);
    }

    @Override
    public UserResponse updateProfile(UserRequest request) {
        User user = getAuthenticateUser();

        if(!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        if(!user.getPhoneNumber().equals(request.getPhoneNumber()) && userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new RuntimeException("Phone Number already exists");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        User updatedUser = userRepository.save(user);

        return userMapper.convertToResponse(updatedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::convertToResponse)
                .toList();
    }

    @Override
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}
