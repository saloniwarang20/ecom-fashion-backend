package com.example.ecom_backend.service;

import com.example.ecom_backend.dto.request.ProductImageRequest;
import com.example.ecom_backend.dto.response.ProductImageResponse;

import java.util.List;

public interface ProductImageService {

    ProductImageResponse addImage(Long productId, ProductImageRequest request);

    ProductImageResponse updateImage(Long imageId, ProductImageRequest request);

    void deleteImage(Long imageId);

    List<ProductImageResponse> getAllImages(Long productId);
}
