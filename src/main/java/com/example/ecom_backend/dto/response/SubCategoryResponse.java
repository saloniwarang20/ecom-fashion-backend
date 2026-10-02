package com.example.ecom_backend.dto.response;

import lombok.Data;

@Data
public class SubCategoryResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private Long categoryId;
    private String categoryName;
}
