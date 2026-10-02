package com.example.ecom_backend.mapper;

import com.example.ecom_backend.dto.response.UserResponse;
import com.example.ecom_backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse convertToResponse(User user){
        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());

        return response;
    }
}
