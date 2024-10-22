package com.example.ecom.repository;

import com.example.ecom.dto.FAQDto;
import com.example.ecom.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FAQRepo extends JpaRepository<FAQ,Long> {
    List<FAQ> findAllByProductId(Long productId);
}
