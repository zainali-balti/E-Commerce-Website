package com.example.ecom.repository;

import com.example.ecom.dto.ReviewDto;
import com.example.ecom.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review,Long> {
    List<Review> findAllByProductId(Long productId);
}
