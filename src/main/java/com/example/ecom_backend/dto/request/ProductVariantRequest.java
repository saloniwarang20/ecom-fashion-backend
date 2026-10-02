package com.example.ecom_backend.dto.request;

import com.example.ecom_backend.enums.Color;
import com.example.ecom_backend.enums.Size;
import lombok.Data;

@Data
public class ProductVariantRequest {
    private Size size;
    private Color color;
    private Integer stock;
    private String sku;
}
