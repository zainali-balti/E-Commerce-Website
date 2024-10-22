package com.example.ecom.services.AdminCoupons;


import com.example.ecom.entity.Coupon;

import java.util.List;

public interface CouponService{
    Coupon createCoupon(Coupon coupon);
    List<Coupon> getAllCoupon();
}
