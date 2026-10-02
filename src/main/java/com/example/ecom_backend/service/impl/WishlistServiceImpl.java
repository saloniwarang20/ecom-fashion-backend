package com.example.ecom_backend.service.impl;

import com.example.ecom_backend.dto.request.WishlistRequest;
import com.example.ecom_backend.dto.response.WishlistResponse;
import com.example.ecom_backend.entity.*;
import com.example.ecom_backend.mapper.WishlistMapper;
import com.example.ecom_backend.repository.*;
import com.example.ecom_backend.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserRepository userRepository;
    private final WishlistMapper wishlistMapper;

    private User getAuthenticateUser(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
    }

    @Override
    public WishlistResponse getWishlist() {
        User user = getAuthenticateUser();

        Wishlist wishlist = wishlistRepository.findByUserId(user.getId())
                .orElseGet(()->{
                    Wishlist newWishlist = new Wishlist();
                    newWishlist.setUser(user);
                    return wishlistRepository.save(newWishlist);
                });
        return wishlistMapper.convertToResponse(wishlist);
    }

    @Override
    public WishlistResponse addProduct(WishlistRequest request) {
        User user = getAuthenticateUser();

        Wishlist wishlist = wishlistRepository.findByUserId(user.getId())
                .orElseGet(()->{
                    Wishlist newWishlist = new Wishlist();
                    newWishlist.setUser(user);
                    return wishlistRepository.save(newWishlist);
                });

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(()->new RuntimeException("Product not found"));

        if(wishlistItemRepository.existsByWishlistIdAndProductId(wishlist.getId(), product.getId())){
            throw new RuntimeException("Product already exists in wishlist");
        }

        WishlistItem item = new WishlistItem();
        item.setWishlist(wishlist);
        item.setProduct(product);
        wishlistItemRepository.save(item);

        return getWishlist();
    }


    @Override
    public void removeProduct(Long wishlistItemId) {
        User user = getAuthenticateUser();

        Wishlist wishlist = wishlistRepository.findByUserId(user.getId())
                .orElseGet(()->{
                    Wishlist newWishlist = new Wishlist();
                    newWishlist.setUser(user);

                    return wishlistRepository.save(newWishlist);
                });

        WishlistItem item = wishlistItemRepository.findById(wishlistItemId)
                .orElseThrow(()-> new RuntimeException("Wishlist Item not found"));

        if(!item.getWishlist().getId().equals(wishlist.getId())){
            throw new RuntimeException("Unauthorized");
        }

        wishlistItemRepository.delete(item);
    }

    @Override
    public void clearWishlist() {
        User user = getAuthenticateUser();

        Wishlist wishlist = wishlistRepository.findByUserId(user.getId())
                .orElseThrow(()-> new RuntimeException("Wishlist not found"));

        wishlist.getWishlistItems().clear();

        wishlistRepository.save(wishlist);
    }
}
