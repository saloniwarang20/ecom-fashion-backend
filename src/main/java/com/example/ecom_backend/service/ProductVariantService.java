package com.example.ecom_backend.service;

import com.example.ecom_backend.dto.request.ProductVariantRequest;
import com.example.ecom_backend.dto.response.ProductVariantResponse;

import java.util.List;

public interface ProductVariantService {

    ProductVariantResponse addVariant(Long productId, ProductVariantRequest request);

    ProductVariantResponse updateVariant(Long id, ProductVariantRequest request);

    void deleteProductVariant(Long id);

    List<ProductVariantResponse> getVariants(Long productId);

    List<ProductVariantResponse> getAllVariants();
}
