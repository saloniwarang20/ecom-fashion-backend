package com.example.ecom_backend.dto.request;

import com.example.ecom_backend.enums.Gender;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
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
}
