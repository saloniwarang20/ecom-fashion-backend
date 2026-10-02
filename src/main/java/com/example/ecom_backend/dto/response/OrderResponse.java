package com.example.ecom_backend.dto.response;

import com.example.ecom_backend.enums.OrderStatus;
import com.example.ecom_backend.enums.PaymentMethod;
import com.example.ecom_backend.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {

    private Long id;
    private String orderNumber;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private BigDecimal shippingCharge;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private AddressResponse address;
    private List<OrderItemResponse> items;
}
