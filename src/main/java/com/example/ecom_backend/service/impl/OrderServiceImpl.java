package com.example.ecom_backend.service.impl;

import com.example.ecom_backend.dto.request.OrderRequest;
import com.example.ecom_backend.dto.response.OrderResponse;
import com.example.ecom_backend.entity.*;
import com.example.ecom_backend.enums.OrderStatus;
import com.example.ecom_backend.enums.PaymentMethod;
import com.example.ecom_backend.enums.PaymentStatus;
import com.example.ecom_backend.mapper.OrderMapper;
import com.example.ecom_backend.repository.*;
import com.example.ecom_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;
    private final ProductVariantRepository productVariantRepository;

    private User getAuthenticateUser(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
    }


    @Override
    public OrderResponse placeOrder(OrderRequest request) {

        User user = getAuthenticateUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(()-> new RuntimeException("Cart not found"));
        if(cart.getCartItems().isEmpty()){
            throw new RuntimeException("Cart is empty");
        }

        Address address = addressRepository.findByIdAndUserId(request.getAddressId(),user.getId())
                        .orElseThrow(()-> new RuntimeException("Address not found"));

        Order order = new Order();

        order.setUser(user);
        order.setAddress(address);
        order.setOrderNumber(String.valueOf(System.currentTimeMillis()));

        order.setDiscountAmount(BigDecimal.ZERO);
        order.setPaymentMethod(request.getPaymentMethod());

        if(request.getPaymentMethod() == PaymentMethod.COD) {
            order.setOrderStatus(OrderStatus.CONFIRMED);
            order.setPaymentStatus(PaymentStatus.PENDING);
        } else {
            order.setOrderStatus(OrderStatus.PENDING);
            order.setPaymentStatus(PaymentStatus.PENDING);
        }

        List<OrderItem> orderItems = new ArrayList<>();

        BigDecimal subtotal = BigDecimal.ZERO;

        for(CartItem cartItem : cart.getCartItems()){

            ProductVariant variant = cartItem.getProductVariant();

            if(cartItem.getQuantity() > variant.getStock()){
                throw new RuntimeException("Not enough stock for "+cartItem.getProduct().getName());
            }

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(cartItem.getProduct());
            item.setProductVariant(cartItem.getProductVariant());
            item.setQuantity(cartItem.getQuantity());

            BigDecimal discountPrice = cartItem.getProduct().getDiscountPrice();

            BigDecimal price = (discountPrice != null && discountPrice.compareTo(BigDecimal.ZERO) > 0)
                    ? discountPrice
                    : cartItem.getProduct().getPrice();

            item.setPrice(price);

            subtotal = subtotal.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

            variant.setStock(variant.getStock() - cartItem.getQuantity());
            productVariantRepository.save(variant);

            orderItems.add(item);

        }

        BigDecimal shippingCharge = subtotal.compareTo(BigDecimal.valueOf(1999)) > 0
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(99);

        order.setShippingCharge(shippingCharge);

        BigDecimal total = subtotal
                .add(order.getShippingCharge())
                .subtract(order.getDiscountAmount());

        order.setTotalAmount(total);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return orderMapper.convertToResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getMyOrders() {
        User user = getAuthenticateUser();

        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(orderMapper::convertToResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrder(Long id) {
        User user = getAuthenticateUser();

        Order order = orderRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(()-> new RuntimeException("Order not found"));

        return orderMapper.convertToResponse(order);
    }

    @Override
    public void cancelOrder(Long id) {
        User user = getAuthenticateUser();

        Order order = orderRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(()-> new RuntimeException("Order not found"));

        if (order.getOrderStatus() == OrderStatus.SHIPPED ||
                order.getOrderStatus() == OrderStatus.OUT_FOR_DELIVERY ||
                order.getOrderStatus() == OrderStatus.DELIVERED ||
                order.getOrderStatus() == OrderStatus.CANCELLED ||
                order.getOrderStatus() == OrderStatus.RETURNED) {

            throw new RuntimeException("This order cannot be cancelled.");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(orderMapper::convertToResponse)
                .toList();
    }

    @Override
    public OrderResponse updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Order not found"));
        order.setOrderStatus(status);

        return orderMapper.convertToResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse getAdminOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return orderMapper.convertToResponse(order);
    }
}
