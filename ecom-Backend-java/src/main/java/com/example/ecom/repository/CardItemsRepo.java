package com.example.ecom.repository;

import com.example.ecom.entity.CardItems;
import com.example.ecom.entity.Order;
import com.example.ecom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardItemsRepo extends JpaRepository<CardItems,Long> {
   Optional<CardItems> findByProductIdAndUserIdAndOrderId(Long productId, Long userId, Long orderId);
}
