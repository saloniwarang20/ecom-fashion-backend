package com.example.ecom_backend.dto.response;

import com.example.ecom_backend.enums.Color;
import com.example.ecom_backend.enums.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WishlistItemResponse {

    private Long id;

    private Long productId;

    private String productName;

    private String brand;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private String primaryImage;

    private boolean available;
}
