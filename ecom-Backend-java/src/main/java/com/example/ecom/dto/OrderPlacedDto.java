package com.example.ecom.dto;

import lombok.Data;

@Data
public class OrderPlacedDto {
    private Long userId;
    private String address;
    private String orderDescription;
}
