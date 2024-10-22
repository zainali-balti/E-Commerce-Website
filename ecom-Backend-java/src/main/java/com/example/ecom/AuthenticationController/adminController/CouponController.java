package com.example.ecom.AuthenticationController.adminController;

import com.example.ecom.Exception.ValidationExcentionForCoupon;
import com.example.ecom.entity.Coupon;
import com.example.ecom.services.AdminCoupons.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @PostMapping("/add")
    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon){
        try {
            Coupon coupon1 = couponService.createCoupon(coupon);
            return ResponseEntity.ok(coupon1);
        }catch (ValidationExcentionForCoupon validationExcentionForCoupon){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationExcentionForCoupon.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Coupon>> getAllCoupons(){
        return ResponseEntity.ok(couponService.getAllCoupon());
    }



}
