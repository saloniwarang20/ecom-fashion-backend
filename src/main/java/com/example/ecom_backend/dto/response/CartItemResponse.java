package com.example.ecom_backend.dto.response;

import com.example.ecom_backend.enums.Color;
import com.example.ecom_backend.enums.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {

    private Long id;

    private Long productId;

    private String productName;

    private Long variantId;

    private String imageUrl;

    private Size size;

    private Color color;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal subtotal;
}
