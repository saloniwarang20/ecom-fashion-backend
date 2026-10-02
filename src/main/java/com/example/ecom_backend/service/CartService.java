package com.example.ecom_backend.service;

import com.example.ecom_backend.dto.request.CartRequest;
import com.example.ecom_backend.dto.response.CartResponse;

public interface CartService {

    CartResponse getCart();

    CartResponse addToCart(CartRequest request);

    CartResponse updateQuantity(Long cartItemId, Integer quantity);

    void removeFromCart(Long cartItemId);

    void clearCart();
}
