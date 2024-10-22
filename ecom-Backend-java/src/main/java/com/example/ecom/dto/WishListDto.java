package com.example.ecom.dto;

import lombok.Data;

@Data
public class WishListDto {
    private Long id;
    private Long userId;
    private Long productId;
    private String productDescription;
    private byte[] returnedImg;
    private Long price;
    private String productName;

}
