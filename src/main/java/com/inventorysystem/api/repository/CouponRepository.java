package com.inventorysystem.api.repository;

import com.inventorysystem.api.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    @Query("select c.discount from Coupon c where c.couponCode =?1")
    Integer findDiscountByCouponCode(String couponCode);
}
