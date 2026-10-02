package com.example.ecom_backend.controller;

import com.example.ecom_backend.dto.request.SubCategoryRequest;
import com.example.ecom_backend.dto.response.SubCategoryResponse;
import com.example.ecom_backend.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @PostMapping("/categories/{categoryId}")
    public SubCategoryResponse createSubCategory(@PathVariable Long categoryId, @RequestBody SubCategoryRequest request){
        return subCategoryService.createSubCategory(categoryId, request);
    }

    @GetMapping
    public List<SubCategoryResponse> getAllSubCategories(){
        return subCategoryService.getAllSubCategories();
    }

    @GetMapping("/category/{categoryId}")
    public List<SubCategoryResponse> getSubCategoryByCategory(@PathVariable Long categoryId){
        return subCategoryService.getSubCategoryByCategory(categoryId);
    }

    @GetMapping("/{id}")
    public SubCategoryResponse getCategoryById(@PathVariable Long id){
        return subCategoryService.getSubCategoryById(id);
    }

    @PutMapping("{id}/categories/{categoryId}")
    public SubCategoryResponse updateSubCategory(@PathVariable Long id, @RequestBody SubCategoryRequest request, @PathVariable Long categoryId){
        return subCategoryService.updateSubCategory(id, request,categoryId);
    }

    @DeleteMapping("/{id}")
    public void deleteSubCategory(@PathVariable Long id){
        subCategoryService.deleteSubCategory(id);
    }
}
