package com.example.ecom_backend.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse {

    private Long cartId;

    private List<CartItemResponse> items;

    private Integer totalItems;

    private BigDecimal totalPrice;
}
