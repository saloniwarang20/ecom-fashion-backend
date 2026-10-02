package com.example.ecom_backend.repository;

import com.example.ecom_backend.entity.ProductVariant;
import com.example.ecom_backend.enums.Color;
import com.example.ecom_backend.enums.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    boolean existsByProductIdAndColorAndSize(Long productId, Color color, Size size);

    boolean existsBySku(String sku);

    List<ProductVariant> findByProductId(Long productId);

    ProductVariant findByProductIdAndColorAndSize(
            Long productId,
            Color color,
            Size size
    );
}
