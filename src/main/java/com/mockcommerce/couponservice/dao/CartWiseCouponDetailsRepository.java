package com.mockcommerce.couponservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mockcommerce.couponservice.entity.CartWiseCouponDetails;

@Repository
public interface CartWiseCouponDetailsRepository extends JpaRepository<CartWiseCouponDetails, Integer> {

    boolean existsByCouponId(Integer id);
}