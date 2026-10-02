package com.example.ecom_backend.dto.request;

import lombok.Data;

@Data
public class ProductImageRequest {
    private String imageUrl;
    private Boolean primaryImage;
}
