package com.example.ecom.dto;

import com.example.ecom.enums.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private Long id;
    private String name;
    private String description;
    private Long amount;
    private Long discount;
    private Long totalAmount;
    private UUID trackingId;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Date date;
    private String userName;
    private List<CardItemsDto> cardItems;
    private String CouponName;
}
