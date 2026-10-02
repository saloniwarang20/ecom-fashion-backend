package com.example.ecom_backend.service;

import com.example.ecom_backend.dto.request.SubCategoryRequest;
import com.example.ecom_backend.dto.response.SubCategoryResponse;

import java.util.List;

public interface SubCategoryService {

    SubCategoryResponse createSubCategory(Long categoryId, SubCategoryRequest request);

    List<SubCategoryResponse> getAllSubCategories();

    List<SubCategoryResponse> getSubCategoryByCategory(Long categoryId);

    SubCategoryResponse getSubCategoryById(Long id);

    SubCategoryResponse updateSubCategory(Long id, SubCategoryRequest request, Long categoryId);

    void deleteSubCategory(Long id);
}
