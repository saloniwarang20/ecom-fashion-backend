package com.example.ecom_backend.service.impl;

import com.example.ecom_backend.dto.request.ReviewRequest;
import com.example.ecom_backend.dto.response.ReviewResponse;
import com.example.ecom_backend.entity.Product;
import com.example.ecom_backend.entity.Review;
import com.example.ecom_backend.entity.User;
import com.example.ecom_backend.mapper.ReviewMapper;
import com.example.ecom_backend.repository.ProductRepository;
import com.example.ecom_backend.repository.ReviewRepository;
import com.example.ecom_backend.repository.UserRepository;
import com.example.ecom_backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;

    private User getAuthenticateUser(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
    }

    @Override
    public ReviewResponse addReview(Long productId, ReviewRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));

        User user = getAuthenticateUser();

        Review review = new Review();

        review.setUser(user);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setProduct(product);

        Review savedReview = reviewRepository.save(review);

        return reviewMapper.convertToResponse(savedReview);
    }

    @Override
    public List<ReviewResponse> getProductReviews(Long productId) {

        return reviewRepository.findByProductId(productId)
                .stream()
                .map(reviewMapper::convertToResponse)
                .toList();
    }

    @Override
    public void deleteReview(Long id) {
        if(!reviewRepository.existsById(id)){
            throw new RuntimeException("Review not found");
        }

        reviewRepository.deleteById(id);
    }

    @Override
    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(reviewMapper::convertToResponse)
                .toList();
    }

    @Override
    public ReviewResponse getReview(Long id) {

        Review review  = reviewRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Review not found"));

        return reviewMapper.convertToResponse(review);
    }
}
