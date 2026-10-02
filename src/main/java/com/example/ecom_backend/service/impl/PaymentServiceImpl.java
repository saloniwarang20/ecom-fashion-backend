package com.example.ecom_backend.service.impl;

import com.example.ecom_backend.dto.request.PaymentRequest;
import com.example.ecom_backend.dto.response.PaymentResponse;
import com.example.ecom_backend.entity.Order;
import com.example.ecom_backend.entity.Payment;
import com.example.ecom_backend.enums.OrderStatus;
import com.example.ecom_backend.enums.PaymentStatus;
import com.example.ecom_backend.mapper.PaymentMapper;
import com.example.ecom_backend.repository.OrderRepository;
import com.example.ecom_backend.repository.PaymentRepository;
import com.example.ecom_backend.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public PaymentResponse makePayment(PaymentRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(()->new RuntimeException("Order not found"));

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.PAID);

        Payment savedPayment = paymentRepository.save(payment);

        order.setPaymentStatus(PaymentStatus.PAID);

        orderRepository.save(order);

        savedPayment.setOrder(order);

        return paymentMapper.convertToResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPayment(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Payment not found"));

        return paymentMapper.convertToResponse(payment);
    }
}
