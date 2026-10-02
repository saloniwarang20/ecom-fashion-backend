package com.example.ecom_backend.controller;

import com.example.ecom_backend.dto.request.WishlistRequest;
import com.example.ecom_backend.dto.response.WishlistResponse;
import com.example.ecom_backend.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public WishlistResponse getWishlist(){
        return wishlistService.getWishlist();
    }

    @PostMapping("/items")
    public WishlistResponse addItemToWishlist(@RequestBody WishlistRequest request){
        return wishlistService.addProduct(request);
    }

    @DeleteMapping("/items/{wishlistItemId}")
    public void removeItem(@PathVariable Long wishlistItemId){
        wishlistService.removeProduct(wishlistItemId);
    }

    @DeleteMapping("/clear")
    public void clearWishlist(){
        wishlistService.clearWishlist();
    }
}
