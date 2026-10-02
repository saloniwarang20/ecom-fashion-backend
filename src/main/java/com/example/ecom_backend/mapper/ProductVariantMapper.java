package com.example.ecom_backend.mapper;

import com.example.ecom_backend.dto.response.ProductVariantResponse;
import com.example.ecom_backend.entity.ProductVariant;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductVariantMapper {

    public ProductVariantResponse convertToResponse(ProductVariant productVariant){
        ProductVariantResponse response = new ProductVariantResponse();

        response.setId(productVariant.getId());
        response.setSku(productVariant.getSku());
        response.setStock(productVariant.getStock());
        response.setSize(productVariant.getSize());
        response.setColor(productVariant.getColor());
        response.setProductId(productVariant.getProduct().getId());
        response.setProductName(productVariant.getProduct().getName());

        return response;
    }
}
