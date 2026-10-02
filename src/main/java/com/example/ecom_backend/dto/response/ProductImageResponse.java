package com.example.ecom_backend.dto.response;

import lombok.Data;

@Data
public class ProductImageResponse {
    private Long id;
    private String imageUrl;
    private Boolean primaryImage;
}
