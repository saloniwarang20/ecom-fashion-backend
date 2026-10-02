package com.example.ecom_backend.repository;

import com.example.ecom_backend.entity.OrderItem;
import com.example.ecom_backend.entity.Product;
import com.example.ecom_backend.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("""
        SELECT oi.product
        FROM OrderItem oi
        JOIN oi.order o
        WHERE o.orderStatus = :status
        GROUP BY oi.product
        ORDER BY SUM(oi.quantity) DESC
    """)
    List<Product> findBestSellingProducts(OrderStatus status);
}
