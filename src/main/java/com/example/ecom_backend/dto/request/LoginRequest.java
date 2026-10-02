package com.example.ecom_backend.dto.request;

import com.example.ecom_backend.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @Email
    private String email;

    @Size(min=8)
    private String password;

}
