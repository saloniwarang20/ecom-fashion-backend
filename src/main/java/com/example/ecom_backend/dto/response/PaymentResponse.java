package com.example.ecom_backend.dto.response;

import com.example.ecom_backend.enums.PaymentMethod;
import com.example.ecom_backend.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentResponse {

    private Long id;

    private String transactionId;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private Long orderId;
}
