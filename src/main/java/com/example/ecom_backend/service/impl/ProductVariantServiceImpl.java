package com.example.ecom_backend.service.impl;

import com.example.ecom_backend.dto.request.ProductVariantRequest;
import com.example.ecom_backend.dto.response.ProductVariantResponse;
import com.example.ecom_backend.entity.Product;
import com.example.ecom_backend.entity.ProductVariant;
import com.example.ecom_backend.mapper.ProductVariantMapper;
import com.example.ecom_backend.repository.ProductRepository;
import com.example.ecom_backend.repository.ProductVariantRepository;
import com.example.ecom_backend.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final ProductVariantMapper productVariantMapper;

    private void updateProductStock(Product product) {

        int totalStock = productVariantRepository
                .findByProductId(product.getId())
                .stream()
                .mapToInt(ProductVariant::getStock)
                .sum();

        product.setStockQuantity(totalStock);

        productRepository.save(product);
    }

    @Override
    public ProductVariantResponse addVariant(Long productId, ProductVariantRequest request) {

        if(productVariantRepository.existsByProductIdAndColorAndSize(productId,request.getColor(),request.getSize())){
            throw new RuntimeException("Product Variant already exists");
        }

        if(productVariantRepository.existsBySku(request.getSku())){
            throw new RuntimeException("SKU already exists");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("Product not found"));
        ProductVariant variant = new ProductVariant();

        variant.setColor(request.getColor());
        variant.setSku(request.getSku());
        variant.setSize(request.getSize());
        variant.setStock(request.getStock());
        variant.setProduct(product);

        ProductVariant savedVariant = productVariantRepository.save(variant);

        updateProductStock(product);

        return productVariantMapper.convertToResponse(savedVariant);
    }

    @Override
    public ProductVariantResponse updateVariant(Long id, ProductVariantRequest request) {

        ProductVariant variant = productVariantRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product Variant not found"));

        ProductVariant existing =
                productVariantRepository
                        .findByProductIdAndColorAndSize(
                                variant.getProduct().getId(),
                                request.getColor(),
                                request.getSize()
                        );

        if(existing != null && !existing.getId().equals(id)){
            throw new RuntimeException("Variant already exists");
        }

        variant.setColor(request.getColor());
        variant.setSku(request.getSku());
        variant.setSize(request.getSize());
        variant.setStock(request.getStock());
        ProductVariant updatedVariant = productVariantRepository.save(variant);
        updateProductStock(variant.getProduct());
        return productVariantMapper.convertToResponse(updatedVariant);
    }

    @Override
    public void deleteProductVariant(Long id) {
        ProductVariant variant = productVariantRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Variant not found"));

        Product product = variant.getProduct();
        productVariantRepository.delete(variant);
        updateProductStock(product);
    }

    @Override
    public List<ProductVariantResponse> getVariants(Long productId) {

        return productVariantRepository.findByProductId(productId)
                .stream()
                .map(productVariantMapper::convertToResponse)
                .toList();
    }

    @Override
    public List<ProductVariantResponse> getAllVariants() {
        return productVariantRepository.findAll()
                .stream()
                .map(productVariantMapper::convertToResponse)
                .toList();
    }
}
