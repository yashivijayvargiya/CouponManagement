package com.mockcommerce.couponservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockcommerce.couponservice.entity.BxGyCouponBuyProducts;
import com.mockcommerce.couponservice.entity.Coupon;
@Repository
public interface CouponRepository extends JpaRepository<Coupon,Integer> {

}
