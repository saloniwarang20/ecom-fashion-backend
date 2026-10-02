package com.example.ecom_backend.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private List<SubCategoryResponse> subCategories;
}
