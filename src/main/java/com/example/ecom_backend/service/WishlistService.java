package com.example.ecom_backend.service;

import com.example.ecom_backend.dto.request.WishlistRequest;
import com.example.ecom_backend.dto.response.WishlistResponse;

public interface WishlistService {

    WishlistResponse getWishlist();

    WishlistResponse addProduct(WishlistRequest request);

    void removeProduct(Long wishlistItemId);

    void clearWishlist();
}
