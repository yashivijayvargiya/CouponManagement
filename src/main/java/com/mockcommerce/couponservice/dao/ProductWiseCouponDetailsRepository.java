package com.mockcommerce.couponservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.mockcommerce.couponservice.entity.Coupon;
import com.mockcommerce.couponservice.entity.ProductWiseCouponDetails;

@Repository
public interface ProductWiseCouponDetailsRepository extends JpaRepository<ProductWiseCouponDetails, Integer> {

    boolean existsByCouponId(Integer id);
}