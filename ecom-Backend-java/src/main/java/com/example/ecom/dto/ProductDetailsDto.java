package com.example.ecom.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetailsDto {
    private ProductDto productDto;
    private List<ReviewDto> reviewDtos;
    private List<FAQDto> faqDtos;
}
