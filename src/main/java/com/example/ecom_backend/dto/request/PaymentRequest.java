package com.example.ecom_backend.dto.request;

import com.example.ecom_backend.enums.PaymentMethod;
import lombok.Data;

@Data
public class PaymentRequest {

    private Long orderId;

    private PaymentMethod paymentMethod;
}
