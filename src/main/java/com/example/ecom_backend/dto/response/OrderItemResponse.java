package com.example.ecom_backend.dto.response;

import com.example.ecom_backend.enums.Color;
import com.example.ecom_backend.enums.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String imageUrl;
    private Color color;
    private Size size;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

}
