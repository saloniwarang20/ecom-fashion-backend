package com.example.ecom_backend.service.impl;

import com.example.ecom_backend.dto.request.ProductImageRequest;
import com.example.ecom_backend.dto.response.ProductImageResponse;
import com.example.ecom_backend.entity.Product;
import com.example.ecom_backend.entity.ProductImage;
import com.example.ecom_backend.mapper.ProductImageMapper;
import com.example.ecom_backend.repository.ProductImageRepository;
import com.example.ecom_backend.repository.ProductRepository;
import com.example.ecom_backend.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final ProductImageMapper productImageMapper;


    @Override
    public ProductImageResponse addImage(Long productId, ProductImageRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));

        ProductImage productImage = new ProductImage();

        if(Boolean.TRUE.equals(request.getPrimaryImage())){

            product.getProductImageList()
                    .forEach(img -> img.setPrimaryImage(false));

        }
        productImage.setImageUrl(request.getImageUrl());
        productImage.setPrimaryImage(request.getPrimaryImage());
        productImage.setProduct(product);

        ProductImage savedImage = productImageRepository.save(productImage);

        return productImageMapper.convertToResponse(savedImage);
    }

    @Override
    public ProductImageResponse updateImage(Long imageId, ProductImageRequest request) {
        ProductImage productImage = productImageRepository.findById(imageId)
                .orElseThrow(()-> new RuntimeException("Image not found"));

        if(Boolean.TRUE.equals(request.getPrimaryImage())){

            productImage.getProduct()
                    .getProductImageList()
                    .forEach(img -> img.setPrimaryImage(false));

        }

        productImage.setImageUrl(request.getImageUrl());
        productImage.setPrimaryImage(request.getPrimaryImage());

        ProductImage updatedImage = productImageRepository.save(productImage);

        return productImageMapper.convertToResponse(updatedImage);
    }

    @Override
    public void deleteImage(Long imageId) {
        if(!productImageRepository.existsById(imageId)){
            throw new RuntimeException("Image not found");
        }
        productImageRepository.deleteById(imageId);
    }

    @Override
    public List<ProductImageResponse> getAllImages(Long productId) {

        return productImageRepository.findByProductId(productId)
                .stream()
                .map(productImageMapper::convertToResponse)
                .toList();
    }
}
