package com.example.ecom_backend.controller;

import com.example.ecom_backend.dto.request.ProductImageRequest;
import com.example.ecom_backend.dto.response.ProductImageResponse;
import com.example.ecom_backend.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-images")
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping("/product/{productId}")
    public ProductImageResponse addImage(@PathVariable Long productId, @RequestBody ProductImageRequest request){
        return productImageService.addImage(productId, request);
    }

    @PutMapping("/{imageId}")
    public ProductImageResponse updateImage(@PathVariable Long imageId, @RequestBody ProductImageRequest request){
        return productImageService.updateImage(imageId,request);
    }

    @DeleteMapping("/{imageId}")
    public void deleteImage(@PathVariable Long imageId){
        productImageService.deleteImage(imageId);
    }

    @GetMapping("/product/{productId}")
    public List<ProductImageResponse> getAllImages(@PathVariable  Long productId){
        return productImageService.getAllImages(productId);
    }
}
