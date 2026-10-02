package com.example.ecom_backend.service;

import com.example.ecom_backend.dto.request.OrderRequest;
import com.example.ecom_backend.dto.response.OrderResponse;
import com.example.ecom_backend.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest request);

    List<OrderResponse> getMyOrders();

    OrderResponse getOrder(Long id);

    void cancelOrder(Long id);

    List<OrderResponse> getAllOrders();

    OrderResponse updateOrderStatus(Long id, OrderStatus status);

    OrderResponse getAdminOrder(Long id);
}
