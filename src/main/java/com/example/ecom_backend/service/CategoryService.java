package com.example.ecom_backend.service;

import com.example.ecom_backend.dto.request.CategoryRequest;
import com.example.ecom_backend.dto.response.CategoryResponse;
import com.example.ecom_backend.entity.Category;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);

    CategoryResponse getCategoryByName(String name);
}
