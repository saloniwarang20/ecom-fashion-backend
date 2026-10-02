package com.example.ecom_backend.mapper;

import com.example.ecom_backend.dto.response.CartItemResponse;
import com.example.ecom_backend.dto.response.CartResponse;
import com.example.ecom_backend.entity.Cart;
import com.example.ecom_backend.entity.ProductImage;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartMapper {

    public CartResponse convertToResponse(Cart cart){
        CartResponse response = new CartResponse();

        response.setCartId(cart.getId());

        List<CartItemResponse> items = cart.getCartItems()
                .stream()
                .map(item -> {
                    CartItemResponse dto = new CartItemResponse();

                    dto.setId(item.getId());
                    dto.setProductId(item.getProduct().getId());
                    dto.setProductName(item.getProduct().getName());
                    dto.setVariantId(item.getProductVariant().getId());
                    dto.setColor(item.getProductVariant().getColor());
                    dto.setSize(item.getProductVariant().getSize());
                    dto.setQuantity(item.getQuantity());
                    dto.setPrice(item.getProduct().getPrice());

                    String imageUrl = item.getProduct()
                            .getProductImageList()
                            .stream()
                            .filter(ProductImage::getPrimaryImage)
                            .findFirst()
                            .map(ProductImage::getImageUrl)
                            .orElse(null);
                    dto.setImageUrl(imageUrl);
                    dto.setSubtotal(
                            item.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
                );

                    return dto;
                })
                .toList();

        response.setItems(items);

        response.setTotalItems(
                items.stream()
                        .mapToInt(CartItemResponse::getQuantity)
                        .sum()
        );

        response.setTotalPrice(
                items.stream()
                        .map(CartItemResponse::getSubtotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        return response;
    }
}
