package com.inventorysystem.api.controller;

import com.inventorysystem.api.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/validate/{couponCode}")
    public int validateCouponCode(@PathVariable("couponCode") String couponCode){
        Integer discount = couponService.validateCoupon(couponCode.toUpperCase());
        if(discount == null)
            return -1;
        else
            return discount;
    }
}
