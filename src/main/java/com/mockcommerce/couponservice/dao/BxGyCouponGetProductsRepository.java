package com.mockcommerce.couponservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockcommerce.couponservice.entity.BxGyCouponGetProducts;

@Repository
public interface BxGyCouponGetProductsRepository extends JpaRepository<BxGyCouponGetProducts, Integer> {
}