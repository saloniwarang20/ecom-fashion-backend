package com.example.ecom_backend.controller;

import com.example.ecom_backend.dto.request.ReviewRequest;
import com.example.ecom_backend.dto.response.ReviewResponse;
import com.example.ecom_backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/product/{productId}")
    public ReviewResponse addReview(@PathVariable Long productId, @RequestBody ReviewRequest request){
        return reviewService.addReview(productId, request);
    }

    @GetMapping("/product/{productId}")
    public List<ReviewResponse> getProductReviews(@PathVariable Long productId){
        return reviewService.getProductReviews(productId);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
    }

    @GetMapping
    public List<ReviewResponse> getAllReviews(){
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ReviewResponse getReview(@PathVariable Long id){
        return reviewService.getReview(id);
    }
}
