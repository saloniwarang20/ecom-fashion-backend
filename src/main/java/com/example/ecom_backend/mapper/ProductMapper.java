package com.example.ecom_backend.mapper;

import com.example.ecom_backend.dto.request.ProductRequest;
import com.example.ecom_backend.dto.response.ProductResponse;
import com.example.ecom_backend.dto.response.ProductVariantResponse;
import com.example.ecom_backend.entity.Product;
import com.example.ecom_backend.entity.ProductVariant;
import com.example.ecom_backend.entity.Review;
import com.example.ecom_backend.entity.SubCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ProductImageMapper productImageMapper;
    private final ProductVariantMapper productVariantMapper;

    public ProductResponse convertToResponse(Product product){
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setBrand(product.getBrand());
        response.setPrice(product.getPrice());
        response.setDiscountPrice(product.getDiscountPrice());
        response.setMaterial(product.getMaterial());
        response.setStockQuantity(product.getStockQuantity());
        response.setGender(product.getGender());
        response.setAvailable(product.isAvailable());
        response.setSubCategoryId(product.getSubCategory().getId());
        response.setSubCategoryName(product.getSubCategory().getName());
        response.setCategoryId(product.getSubCategory().getCategory().getId());
        response.setCategoryName(product.getSubCategory().getCategory().getName());
        response.setVariants(product.getProductVariantList()
                .stream()
                .map(productVariantMapper::convertToResponse)
                .toList());
        response.setImages(product.getProductImageList()
                .stream()
                .map(productImageMapper::convertToResponse)
                .toList());

        List<Review> reviews = product.getReviewList();
        double averageRating = reviews.isEmpty()
                ? 0.0
                : reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        response.setAverageRating(averageRating);
        response.setReviewCount(reviews.size());

        return response;
    }

    public void mapRequestToProduct(Product product, ProductRequest request, SubCategory subCategory){
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setDiscountPrice(request.getDiscountPrice());
        product.setMaterial(request.getMaterial());
        product.setGender(request.getGender());
        product.setAvailable(request.isAvailable());
        product.setSubCategory(subCategory);
    }
}
