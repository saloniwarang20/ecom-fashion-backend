package com.example.ecom_backend.controller;

import com.example.ecom_backend.dto.request.ProductVariantRequest;
import com.example.ecom_backend.dto.response.ProductVariantResponse;
import com.example.ecom_backend.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-variants")
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    @PostMapping("/product/{productId}")
    public ProductVariantResponse addVariant(@PathVariable Long productId, @RequestBody ProductVariantRequest request){
        return productVariantService.addVariant(productId,request);
    }

    @PutMapping("/{id}")
    public ProductVariantResponse updateVariant(@PathVariable Long id,@RequestBody ProductVariantRequest request){
        return productVariantService.updateVariant(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteVariant(@PathVariable Long id){
        productVariantService.deleteProductVariant(id);
    }

    @GetMapping("/product/{productId}")
    public List<ProductVariantResponse> getAllVariant(@PathVariable Long productId){
        return productVariantService.getVariants(productId);
    }

    @GetMapping
    public List<ProductVariantResponse> getAllVariants(){
        return productVariantService.getAllVariants();
    }

}
