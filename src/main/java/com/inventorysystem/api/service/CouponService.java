package com.inventorysystem.api.service;

import com.inventorysystem.api.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public Integer validateCoupon(String couponCode){
        Integer discount = couponRepository.findDiscountByCouponCode(couponCode);
        if( discount != null){
            return discount;
        }
        else
            return null;
    }
}
