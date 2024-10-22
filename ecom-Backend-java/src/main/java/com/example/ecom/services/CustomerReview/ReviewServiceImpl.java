package com.example.ecom.services.CustomerReview;

import com.example.ecom.dto.ProductDto;
import com.example.ecom.dto.OrderProductDetailList;
import com.example.ecom.dto.ReviewDto;
import com.example.ecom.entity.*;
import com.example.ecom.repository.OrderRepo;
import com.example.ecom.repository.ProductRepo;
import com.example.ecom.repository.ReviewRepo;
import com.example.ecom.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements  ReviewService{
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ReviewRepo reviewRepo;

    public OrderProductDetailList getAllReviewsByOrderId(Long orderId){
        Optional<Order> orderOptional = orderRepo.findById(orderId);
        OrderProductDetailList reviewDto = new OrderProductDetailList();
        if (orderOptional.isPresent()){
            reviewDto.setOrderAmount(orderOptional.get().getAmount());
            List<ProductDto> productDto = new ArrayList<>();
            for (CardItems card: orderOptional.get().getCardItems()
                 ) {
                ProductDto product = new ProductDto();
                product.setId(card.getProduct().getId());
                product.setName(card.getProduct().getName());
                product.setPrice(card.getPrice());
                product.setImg(card.getProduct().getImg());
                product.setQuantity(card.getQuantity());
                productDto.add(product);
            }
            reviewDto.setProductDtos(productDto);
        }
        return reviewDto;
    }

    public ReviewDto postReviews(ReviewDto reviewDto, MultipartFile imgFile){
        Optional<Product> optionalProduct = productRepo.findById(reviewDto.getProductId());
        Optional<User> optionalUser = userRepo.findById(reviewDto.getUserId());
        if (optionalUser.isPresent() && optionalProduct.isPresent()){
            Review review = new Review();
            review.setDescription(reviewDto.getDescription());
            review.setUser(optionalUser.get());
            review.setProduct(optionalProduct.get());
            try {
                if (imgFile != null && !imgFile.isEmpty()) {
                    review.setImg(imgFile.getBytes());
                }
            } catch (IOException e) {
                throw new RuntimeException("Error reading image file", e);
            }
            review.setRating(reviewDto.getRating());
            reviewRepo.save(review);
            return review.getReviewDto();
        }
        return null;
    }
}
