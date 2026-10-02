package com.example.ecom_backend.controller;

import com.example.ecom_backend.dto.request.CartRequest;
import com.example.ecom_backend.dto.request.UpdateCartQuantityRequest;
import com.example.ecom_backend.dto.response.CartResponse;
import com.example.ecom_backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartResponse getCart(){
        return cartService.getCart();
    }

    @PostMapping("/items")
    public CartResponse addToCart(@RequestBody CartRequest request){
        return cartService.addToCart(request);
    }

    @PutMapping("/items/{cartItemId}")
    public CartResponse updateQuantity(@PathVariable Long cartItemId, @RequestBody UpdateCartQuantityRequest request){
        return cartService.updateQuantity(cartItemId, request.getQuantity());
    }

    @DeleteMapping("/items/{cartItemId}")
    public void removeFromCart(@PathVariable Long cartItemId){
        cartService.removeFromCart(cartItemId);
    }

    @DeleteMapping("/clear")
    public void clearCart(){
        cartService.clearCart();
    }
}
