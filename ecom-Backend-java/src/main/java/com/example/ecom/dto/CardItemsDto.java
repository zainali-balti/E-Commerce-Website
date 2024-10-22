package com.example.ecom.dto;

import lombok.Data;

@Data
public class CardItemsDto {
        private Long id;
        private Long price;
        private Long productId;
        private Long userId;
        private Long orderId;
        private byte[] returnedImg;
        private String productName;
        private Long quantity;
}
