package com.example.ecom_backend.dto.response;

import com.example.ecom_backend.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserResponse {

    @NotBlank
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @Pattern( regexp = "^[6-9]\\d{9}$")
    private String phoneNumber;

    @NotBlank
    private Role role;

}
