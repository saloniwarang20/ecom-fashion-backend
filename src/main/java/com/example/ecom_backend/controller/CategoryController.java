package com.example.ecom_backend.controller;

import com.example.ecom_backend.dto.request.CategoryRequest;
import com.example.ecom_backend.dto.response.CategoryResponse;
import com.example.ecom_backend.entity.Category;
import com.example.ecom_backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest request){
        return categoryService.createCategory(request);
    };

    @GetMapping
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request){
        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }

    @GetMapping("/name/{name}")
    public CategoryResponse getCategoryByName(@PathVariable String name){
        return categoryService.getCategoryByName(name);
    }

}
