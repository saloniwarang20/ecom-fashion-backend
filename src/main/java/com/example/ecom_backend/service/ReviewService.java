package com.example.ecom_backend.service;

import com.example.ecom_backend.dto.request.ReviewRequest;
import com.example.ecom_backend.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse addReview(Long productId, ReviewRequest request);

    List<ReviewResponse> getProductReviews(Long productId);

    void deleteReview(Long id);

    List<ReviewResponse> getAllReviews();

    ReviewResponse getReview(Long id);
}
