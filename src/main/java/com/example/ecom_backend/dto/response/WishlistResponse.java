package com.example.ecom_backend.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class WishlistResponse {

    private Long id;

    private Integer totalItems;

    private List<WishlistItemResponse> items;
}
