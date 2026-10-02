package com.example.ecom_backend.repository;

import com.example.ecom_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    boolean existsByNameAndSubCategoryId(String name, Long subCategoryId);

    List<Product> findBySubCategoryId(Long subCategoryId);

    List<Product> findByBrandIgnoreCase(String brand);

    List<Product> findByNameContainingIgnoreCase(String keyword);
}
