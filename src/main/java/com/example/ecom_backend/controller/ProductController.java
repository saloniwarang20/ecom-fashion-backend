package com.example.ecom_backend.controller;

import com.example.ecom_backend.dto.request.ProductRequest;
import com.example.ecom_backend.dto.response.ProductResponse;
import com.example.ecom_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductRequest request){
        return productService.createProduct(request);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequest request){
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @GetMapping("/best-sellers")
    public List<ProductResponse> getBestSellers(){
        return productService.getBestSellers();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/subcategory/{subCategoryId}")
    public List<ProductResponse> getProductByCategoryId(@PathVariable Long subCategoryId){
        return productService.getProductBySubCategory(subCategoryId);
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProducts(@RequestParam String keyword){
        return productService.searchProducts(keyword);
    }

    @GetMapping("/brand/{brand}")
    public List<ProductResponse> getProductByBrand(@PathVariable String brand){
        return productService.getProductByBrand(brand);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponse>> filterProducts(
            @RequestParam(required = false) Long subCategoryId,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) List<String> colors,
            @RequestParam(required = false) List<String> sizes
    ){
        return ResponseEntity.ok(
                productService.filterProducts(
                        subCategoryId,
                        brand,
                        minPrice,
                        maxPrice,
                        colors,
                        sizes
                )
        );
    }

    @GetMapping("/recommended")
    public List<ProductResponse> getRecommendedProduct(){
        return productService.getRecommendedProducts();
    }





}
