package com.example.ecom_backend.mapper;

import com.example.ecom_backend.dto.response.PaymentResponse;
import com.example.ecom_backend.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentResponse convertToResponse(Payment payment){
        PaymentResponse response = new PaymentResponse();

        response.setId(payment.getId());
        response.setAmount(payment.getAmount());
        response.setTransactionId(payment.getTransactionId());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setOrderId(payment.getOrder().getId());

        return response;
    }
}
