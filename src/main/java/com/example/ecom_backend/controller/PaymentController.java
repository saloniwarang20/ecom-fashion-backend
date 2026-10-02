package com.example.ecom_backend.controller;

import com.example.ecom_backend.dto.request.PaymentRequest;
import com.example.ecom_backend.dto.response.PaymentResponse;
import com.example.ecom_backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponse makePayment(@RequestBody PaymentRequest request){
        return paymentService.makePayment(request);
    }

    @GetMapping("/{id}")
    public PaymentResponse getPayment(@PathVariable Long id){
        return paymentService.getPayment(id);
    }
}
