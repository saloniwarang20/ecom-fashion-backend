package com.example.ecom_backend.dto.request;

import lombok.Data;

@Data
public class CartRequest {

    private Long productId;

    private Long productVariantId;

    private Integer quantity;
}
