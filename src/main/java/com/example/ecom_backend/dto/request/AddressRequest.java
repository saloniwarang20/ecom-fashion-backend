package com.example.ecom_backend.dto.request;

import lombok.Data;

@Data
public class AddressRequest {

    private String fullName;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private boolean isDefault;
}
