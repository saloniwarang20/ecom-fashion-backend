package com.example.ecom_backend.dto.response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponse {

    private Long id;

    @Min(1)
    @Max(5)
    private Integer rating;

    private String comment;

    private String userName;

    private LocalDateTime createdAt;

    private Long productId;

    private String productName;

}
