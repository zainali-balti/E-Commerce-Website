package com.example.ecom.AuthenticationController.CustomerController;

import com.example.ecom.dto.OrderProductDetailList;
import com.example.ecom.dto.ProductDto;
import com.example.ecom.dto.ReviewDto;
import com.example.ecom.services.CustomerReview.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/customer/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderProductDetailList> getReviewsByOrderId(@PathVariable Long orderId){
        return ResponseEntity.ok(reviewService.getAllReviewsByOrderId(orderId));
    }
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> giveReviews(
            @RequestPart("review") ReviewDto reviewDto,
            @RequestPart(value = "img", required = false) MultipartFile imgFile) {
        System.out.println("ReviewDto received: " + reviewDto);
        if (imgFile != null) {
            System.out.println("Image file received: " + imgFile.getOriginalFilename());
        }

        ReviewDto reviewDto1 = reviewService.postReviews(reviewDto, imgFile);
        if (reviewDto1 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewDto1);
    }
}
