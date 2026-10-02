package com.example.ecom_backend.controller;

import com.example.ecom_backend.dto.request.OrderRequest;
import com.example.ecom_backend.dto.response.OrderResponse;
import com.example.ecom_backend.enums.OrderStatus;
import com.example.ecom_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse placeOrder(@RequestBody OrderRequest request){
        return orderService.placeOrder(request);
    }

    @GetMapping
    public List<OrderResponse> getMyOrders(){
        return orderService.getMyOrders();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id){
        return orderService.getOrder(id);
    }

    @PutMapping("/{id}/cancel")
    public void cancelOrder(@PathVariable Long id){
        orderService.cancelOrder(id);
    }

    @GetMapping("/admin")
    public List<OrderResponse> getAllOrders(){
        return orderService.getAllOrders();
    }

    @PutMapping("/admin/{id}")
    public OrderResponse updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status){
        return orderService.updateOrderStatus(id, status);
    }

    @GetMapping("/admin/{id}")
    public OrderResponse getAdminOrder(@PathVariable Long id){
        return orderService.getAdminOrder(id);
    }
}
