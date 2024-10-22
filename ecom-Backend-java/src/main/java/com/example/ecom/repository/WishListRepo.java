package com.example.ecom.repository;

import com.example.ecom.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepo extends JpaRepository<WishList,Long> {
    List<WishList> findAllByuserId(Long userId);
}
