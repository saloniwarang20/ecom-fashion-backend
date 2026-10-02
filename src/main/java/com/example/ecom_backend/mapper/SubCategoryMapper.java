package com.example.ecom_backend.mapper;

import com.example.ecom_backend.dto.response.SubCategoryResponse;
import com.example.ecom_backend.entity.SubCategory;
import org.springframework.stereotype.Component;

@Component
public class SubCategoryMapper {
    public SubCategoryResponse convertToResponse(SubCategory subCategory){
        SubCategoryResponse response = new SubCategoryResponse();
        response.setId(subCategory.getId());
        response.setName(subCategory.getName());
        response.setImageUrl(subCategory.getImageUrl());
        response.setCategoryId(subCategory.getCategory().getId());
        response.setCategoryName(subCategory.getCategory().getName());

        return response;
    }
}
