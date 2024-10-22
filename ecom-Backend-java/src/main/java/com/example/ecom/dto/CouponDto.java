package com.example.ecom.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CouponDto {
    private Long id;
    private String name;
    private String code;
    private Date expirationDate;
    private Long discount;
}
