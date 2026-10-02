package com.example.ecom_backend.service.impl;

import com.example.ecom_backend.dto.request.CartRequest;
import com.example.ecom_backend.dto.response.CartResponse;
import com.example.ecom_backend.entity.*;
import com.example.ecom_backend.mapper.CartMapper;
import com.example.ecom_backend.repository.*;
import com.example.ecom_backend.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;

    private User getAuthenticateUser(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
    }

    @Override
    public CartResponse getCart() {
        User user = getAuthenticateUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(()->{
                    Cart newCart = new Cart();
                    newCart.setUser(user);

                    return cartRepository.save(newCart);
                });

        return cartMapper.convertToResponse(cart);
    }

    @Override
    public CartResponse addToCart(CartRequest request) {
        User user = getAuthenticateUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(()->{
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(()->new RuntimeException("Product not found"));

        ProductVariant productVariant = productVariantRepository.findById(request.getProductVariantId())
                .orElseThrow(()-> new RuntimeException("Variant not found"));

        if(!productVariant.getProduct().getId().equals(product.getId())){
            throw new RuntimeException("Variant does not belong to product");
        }

        CartItem item = cartItemRepository.findByCartIdAndProductVariantId(cart.getId(),productVariant.getId())
                .orElse(null);

        if(request.getQuantity() <= 0){
            throw new RuntimeException("Invalid quantity");
        }

        if(item != null){

            int totalQuantity = item.getQuantity() + request.getQuantity();

            if(totalQuantity > productVariant.getStock()){
                throw new RuntimeException("Not enough stock");
            }

            item.setQuantity(item.getQuantity() + request.getQuantity());
        }else{

            if(request.getQuantity() > productVariant.getStock()){
                throw new RuntimeException("Not enough stock");
            }

            item = new CartItem();
            item.setCart(cart);
            item.setQuantity(request.getQuantity());
            item.setProduct(product);
            item.setProductVariant(productVariant);
        }

        cartItemRepository.save(item);
        return getCart();
    }

    @Override
    public CartResponse updateQuantity(Long cartItemId, Integer quantity) {

        User user = getAuthenticateUser();

        CartItem item = cartItemRepository.findByIdAndCartUserId(cartItemId, user.getId())
                .orElseThrow(()->new RuntimeException("Cart Item not found"));

        if(quantity <= 0){
            throw new RuntimeException("Quantity must be greater than zero");
        }

        if(quantity > item.getProductVariant().getStock()){
            throw new RuntimeException("Not enough stock");
        }

        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return getCart();
    }

    @Override
    public void removeFromCart(Long cartItemId) {
        User user = getAuthenticateUser();

        CartItem item = cartItemRepository.findByIdAndCartUserId(cartItemId, user.getId())
                .orElseThrow(()->new RuntimeException("Cart Item not found"));

        cartItemRepository.delete(item);
    }

    @Override
    public void clearCart() {
        User user = getAuthenticateUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(()-> new RuntimeException("Cart not found"));

        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}
