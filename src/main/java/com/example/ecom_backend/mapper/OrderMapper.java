package com.example.ecom_backend.mapper;

import com.example.ecom_backend.dto.response.OrderItemResponse;
import com.example.ecom_backend.dto.response.OrderResponse;
import com.example.ecom_backend.entity.Order;
import com.example.ecom_backend.entity.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final AddressMapper addressMapper;

    public OrderResponse convertToResponse(Order order){
        OrderResponse response = new OrderResponse();

        response.setId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setOrderStatus(order.getOrderStatus());

        response.setPaymentStatus(order.getPaymentStatus());
        response.setPaymentMethod(order.getPaymentMethod());

        response.setShippingCharge(order.getShippingCharge());
        response.setDiscountAmount(order.getDiscountAmount());
        response.setTotalAmount(order.getTotalAmount());
        response.setCreatedAt(order.getCreatedAt());
        response.setAddress(addressMapper.convertToResponse(order.getAddress()));

        List<OrderItemResponse> items = order.getItems()
                .stream()
                .map(item -> {
                    OrderItemResponse dto = new OrderItemResponse();

                    dto.setId(item.getId());
                    dto.setProductId(item.getProduct().getId());
                    dto.setProductName(item.getProduct().getName());
                    dto.setSize(item.getProductVariant().getSize());
                    dto.setColor(item.getProductVariant().getColor());
                    dto.setQuantity(item.getQuantity());
                    dto.setPrice(item.getPrice());
                    dto.setTotalPrice(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));


                    String imageUrl = item.getProduct()
                            .getProductImageList()
                            .stream()
                            .filter(ProductImage::getPrimaryImage)
                            .findFirst()
                            .map(ProductImage::getImageUrl)
                            .orElse(null);

                    dto.setImageUrl(imageUrl);

                    return dto;
                })
                .toList();

        response.setItems(items);

        return response;
    }

}
