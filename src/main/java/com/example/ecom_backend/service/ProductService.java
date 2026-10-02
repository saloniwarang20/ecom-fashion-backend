package com.example.ecom_backend.service;

import com.example.ecom_backend.dto.request.ProductRequest;
import com.example.ecom_backend.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Long id,ProductRequest request);

    void deleteProduct(Long id);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getProductBySubCategory(Long subCategoryId);

    List<ProductResponse> searchProducts(String keyword);

    List<ProductResponse> getProductByBrand(String brand);

    List<ProductResponse> filterProducts(
            Long subCategoryId,
            String brand,
            Double minPrice,
            Double maxPrice,
            List<String> colors,
            List<String> sizes
    );

    List<ProductResponse> getRecommendedProducts();

    List<ProductResponse> getBestSellers();

}
