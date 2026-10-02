package com.example.ecom_backend.dto.response;

import com.example.ecom_backend.enums.Color;
import com.example.ecom_backend.enums.Size;
import lombok.Data;

import java.util.List;

@Data
public class ProductVariantResponse {
    private Long id;
    private Size size;
    private Color color;
    private Integer stock;
    private String sku;
    private Long productId;
    private String productName;
}
