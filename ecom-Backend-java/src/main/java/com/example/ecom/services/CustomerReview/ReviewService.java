package com.example.ecom.services.CustomerReview;

import com.example.ecom.dto.OrderProductDetailList;
import com.example.ecom.dto.ReviewDto;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {
    OrderProductDetailList getAllReviewsByOrderId(Long orderId);
    ReviewDto postReviews(ReviewDto reviewDto, MultipartFile imgFile);
}
