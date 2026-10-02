package com.example.ecom_backend.service;

import com.example.ecom_backend.dto.request.PaymentRequest;
import com.example.ecom_backend.dto.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse makePayment(PaymentRequest request);

    PaymentResponse getPayment(Long id);
}
