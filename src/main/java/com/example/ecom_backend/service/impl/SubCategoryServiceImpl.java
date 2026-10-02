package com.example.ecom_backend.service.impl;

import com.example.ecom_backend.dto.request.SubCategoryRequest;
import com.example.ecom_backend.dto.response.SubCategoryResponse;
import com.example.ecom_backend.entity.Category;
import com.example.ecom_backend.entity.SubCategory;
import com.example.ecom_backend.mapper.SubCategoryMapper;
import com.example.ecom_backend.repository.CategoryRepository;
import com.example.ecom_backend.repository.SubCategoryRepository;
import com.example.ecom_backend.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryMapper subCategoryMapper;

    @Override
    public SubCategoryResponse createSubCategory(Long categoryId, SubCategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new RuntimeException("Category not found"));

        if(subCategoryRepository.existsByNameAndCategoryId(request.getName(), categoryId)){
            throw new RuntimeException("Sub Category already exists");
        }

        SubCategory subCategory = new SubCategory();
        subCategory.setName(request.getName());
        subCategory.setImageUrl(request.getImageUrl());
        subCategory.setCategory(category);

        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        return subCategoryMapper.convertToResponse(savedSubCategory);
    }

    @Override
    public List<SubCategoryResponse> getAllSubCategories() {
        List<SubCategory> subCategories = subCategoryRepository.findAll();

        return subCategories.stream()
                .map(subCategoryMapper::convertToResponse)
                .toList();
    }

    @Override
    public List<SubCategoryResponse> getSubCategoryByCategory(Long categoryId) {
        if(!categoryRepository.existsById(categoryId)){
            throw new RuntimeException("Category not found");
        }
        return subCategoryRepository.findByCategoryId(categoryId)
                .stream()
                .map(subCategoryMapper::convertToResponse)
                .toList();
    }

    @Override
    public SubCategoryResponse getSubCategoryById(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Sub Category not found"));

        return subCategoryMapper.convertToResponse(subCategory);
    }

    @Override
    public SubCategoryResponse updateSubCategory(Long id, SubCategoryRequest request, Long categoryId) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Sub Category not found"));

        if(!subCategory.getName().equals(request.getName()) && subCategoryRepository.existsByNameAndCategoryId(request.getName(), categoryId)){
            throw new RuntimeException("Sub Category already exists");
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new RuntimeException("Category not found"));

        subCategory.setName(request.getName());
        subCategory.setImageUrl(request.getImageUrl());
        subCategory.setCategory(category);

        SubCategory updatedSubCategory = subCategoryRepository.save(subCategory);
        return subCategoryMapper.convertToResponse(updatedSubCategory);
    }

    @Override
    public void deleteSubCategory(Long id) {
        if(!subCategoryRepository.existsById(id)){
            throw new RuntimeException("Sub Category not found");
        }
        subCategoryRepository.deleteById(id);
    }
}
