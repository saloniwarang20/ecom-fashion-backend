package com.example.ecom_backend.service.impl;

import com.example.ecom_backend.dto.request.CategoryRequest;
import com.example.ecom_backend.dto.response.CategoryResponse;
import com.example.ecom_backend.entity.Category;
import com.example.ecom_backend.mapper.CategoryMapper;
import com.example.ecom_backend.repository.CategoryRepository;
import com.example.ecom_backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        if(categoryRepository.existsByName(request.getName())){
            throw new RuntimeException("Category name already exists");
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.convertToResponse(savedCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(categoryMapper::convertToResponse)
                .toList();
    }


    @Override
    public CategoryResponse getCategoryById(Long id){

        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Category not found"));

        return categoryMapper.convertToResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Category not found"));

        if(!category.getName().equals(request.getName()) && categoryRepository.existsByName(request.getName())){
            throw new RuntimeException("Category already exists");
        }
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());

        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.convertToResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)){
            throw new RuntimeException("Category not found");
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponse getCategoryByName(String name) {
        Category category = categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(()-> new RuntimeException("Category not found"));

        return categoryMapper.convertToResponse(category);
    }
}
