package com.example.ecom_backend.dto.request;

import lombok.Data;

@Data
public class CategoryRequest {
    private String name;
    private String description;
    private String imageUrl;
}
