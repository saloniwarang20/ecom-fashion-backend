package com.example.ecom_backend.dto.response;

import com.example.ecom_backend.enums.Gender;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String material;
    private Gender gender;
    private Integer stockQuantity;
    private boolean available;
    private Long subCategoryId;
    private String subCategoryName;
    private Long categoryId;
    private String categoryName;
    private Double averageRating;
    private Integer reviewCount;
    private List<ProductImageResponse> images;
    private List<ProductVariantResponse> variants;

}
