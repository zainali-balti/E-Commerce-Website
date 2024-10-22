package com.example.ecom.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderProductDetailList {
    private List<ProductDto> productDtos;
    private Long orderAmount;
}
