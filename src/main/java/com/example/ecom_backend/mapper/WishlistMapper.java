package com.example.ecom_backend.mapper;

import com.example.ecom_backend.dto.response.WishlistItemResponse;
import com.example.ecom_backend.dto.response.WishlistResponse;
import com.example.ecom_backend.entity.Product;
import com.example.ecom_backend.entity.ProductImage;
import com.example.ecom_backend.entity.Wishlist;
import com.example.ecom_backend.entity.WishlistItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WishlistMapper {

    private final ProductMapper productMapper;

    public WishlistResponse convertToResponse(Wishlist wishlist){
        WishlistResponse response = new WishlistResponse();

        response.setId(wishlist.getId());

        List<WishlistItemResponse> items = wishlist.getWishlistItems() == null
                ?List.of()
                :wishlist.getWishlistItems()
                .stream()
                .map(this::convertItemToResponse)
                .toList();

        response.setItems(items);
        response.setTotalItems(items.size());

        return response;
    }

    public WishlistItemResponse convertItemToResponse(WishlistItem item){
        Product product = item.getProduct();
        WishlistItemResponse response = new WishlistItemResponse();

        response.setId(item.getId());

        response.setProductId(product.getId());
        response.setProductName(product.getName());

        response.setBrand(product.getBrand());
        response.setPrice(product.getPrice());
        response.setDiscountPrice(product.getDiscountPrice());
        response.setAvailable(product.isAvailable());

        String primaryImage = product.getProductImageList()
                .stream()
                .filter(ProductImage::getPrimaryImage)
                .map(ProductImage::getImageUrl)
                .findFirst()
                .orElse(null);

        response.setPrimaryImage(primaryImage);

        return response;
    }
}
