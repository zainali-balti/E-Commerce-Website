package com.example.ecom.services.AdminCoupons;

import com.example.ecom.Exception.ValidationExcentionForCoupon;
import com.example.ecom.entity.Coupon;
import com.example.ecom.repository.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService{
    @Autowired
    private CouponRepo couponRepo;
    public Coupon createCoupon(Coupon coupon){
        if (couponRepo.existsByCode(coupon.getCode())){
            throw new ValidationExcentionForCoupon("Coupon Code Was already Exist");
        }
        return couponRepo.save(coupon);
    }
    public List<Coupon> getAllCoupon(){
        return couponRepo.findAll();
    }
}
