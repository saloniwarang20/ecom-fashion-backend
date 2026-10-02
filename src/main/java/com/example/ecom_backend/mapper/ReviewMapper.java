package com.example.ecom_backend.mapper;

import com.example.ecom_backend.dto.response.ReviewResponse;
import com.example.ecom_backend.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewResponse convertToResponse(Review review){
        ReviewResponse response = new ReviewResponse();

        response.setId(review.getId());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setUserName(review.getUser().getFirstName());
        response.setCreatedAt(review.getCreatedAt());
        response.setProductId(review.getProduct().getId());
        response.setProductName(review.getProduct().getName());

        return response;
    }
}
