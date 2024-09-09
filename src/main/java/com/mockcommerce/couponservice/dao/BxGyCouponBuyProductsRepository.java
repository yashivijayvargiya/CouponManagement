package com.mockcommerce.couponservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockcommerce.couponservice.entity.BxGyCouponBuyProducts;

@Repository
public interface BxGyCouponBuyProductsRepository extends JpaRepository<BxGyCouponBuyProducts, Integer> {


}