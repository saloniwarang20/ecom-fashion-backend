package com.example.ecom_backend.mapper;

import com.example.ecom_backend.dto.response.CategoryResponse;
import com.example.ecom_backend.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse convertToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setImageUrl(category.getImageUrl());
        return response;
    }
}
