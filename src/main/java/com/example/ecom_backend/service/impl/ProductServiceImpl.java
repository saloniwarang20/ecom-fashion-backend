package com.example.ecom_backend.service.impl;

import com.example.ecom_backend.dto.request.ProductRequest;
import com.example.ecom_backend.dto.response.ProductResponse;
import com.example.ecom_backend.entity.Product;
import com.example.ecom_backend.entity.ProductVariant;
import com.example.ecom_backend.entity.SubCategory;
import com.example.ecom_backend.enums.OrderStatus;
import com.example.ecom_backend.mapper.ProductMapper;
import com.example.ecom_backend.repository.OrderItemRepository;
import com.example.ecom_backend.repository.ProductRepository;
import com.example.ecom_backend.repository.SubCategoryRepository;
import com.example.ecom_backend.service.ProductService;
import com.example.ecom_backend.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductMapper productMapper;
    private final OrderItemRepository orderItemRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        if(productRepository.existsByNameAndSubCategoryId(request.getName(), request.getSubCategoryId())){
            throw new RuntimeException("Product already exists");
        }
        SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                .orElseThrow(()->new RuntimeException("Sub Category not found"));

        Product product = new Product();

        productMapper.mapRequestToProduct(product, request,subCategory);

        product.setStockQuantity(0);

        Product savedProduct = productRepository.save(product);
        return productMapper.convertToResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found"));
        if(!product.getName().equals(request.getName()) && productRepository.existsByNameAndSubCategoryId(request.getName(), request.getSubCategoryId())){
            throw new RuntimeException("Product already exists");
        }

        SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                        .orElseThrow(()-> new RuntimeException("Sub category not found"));

        productMapper.mapRequestToProduct(product, request,subCategory);

        Product updatedProduct = productRepository.save(product);

        return productMapper.convertToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)){
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product not Found"));
        return productMapper.convertToResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::convertToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getProductBySubCategory(Long subCategoryId) {
        if(!subCategoryRepository.existsById(subCategoryId)){
            throw new RuntimeException("Sub Category not found");
        }

        return productRepository.findBySubCategoryId(subCategoryId)
                .stream()
                .map(productMapper::convertToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(productMapper::convertToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getProductByBrand(String brand) {
        return productRepository.findByBrandIgnoreCase(brand)
                .stream()
                .map(productMapper::convertToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> filterProducts(Long subCategoryId, String brand, Double minPrice, Double maxPrice, List<String> colors, List<String> sizes) {
        Specification<Product> specification = Specification.where(ProductSpecification.hasSubCategory(subCategoryId))
                .and(ProductSpecification.hasBrand(brand))
                .and(ProductSpecification.minPrice(minPrice))
                .and(ProductSpecification.maxPrice(maxPrice))
                .and(ProductSpecification.hasColors(colors))
                .and(ProductSpecification.hasSizes(sizes));
        return productRepository.findAll(specification)
                .stream()
                .map(productMapper::convertToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getRecommendedProducts() {
        List<Product> products = productRepository.findAll();
        Collections.shuffle(products);

        return products.stream()
                .limit(8)
                .map(productMapper::convertToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getBestSellers() {
        List<Product> products = orderItemRepository.findBestSellingProducts(OrderStatus.DELIVERED);

        return products.stream()
                .map(productMapper::convertToResponse)
                .toList();
    }
}
