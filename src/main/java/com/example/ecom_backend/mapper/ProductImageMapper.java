package com.example.ecom_backend.mapper;

import com.example.ecom_backend.dto.response.ProductImageResponse;
import com.example.ecom_backend.entity.ProductImage;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapper {

    public ProductImageResponse convertToResponse(ProductImage productImage){
        ProductImageResponse response = new ProductImageResponse();

        response.setId(productImage.getId());
        response.setImageUrl(productImage.getImageUrl());
        response.setPrimaryImage(productImage.getPrimaryImage());

        return response;

    }
}
