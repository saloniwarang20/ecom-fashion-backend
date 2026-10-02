package com.example.ecom_backend.dto.request;

import com.example.ecom_backend.enums.PaymentMethod;
import lombok.Data;

@Data
public class OrderRequest {

    private Long addressId;

    private PaymentMethod paymentMethod;
}
