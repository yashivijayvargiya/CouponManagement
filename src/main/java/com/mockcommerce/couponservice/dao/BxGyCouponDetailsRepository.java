package com.mockcommerce.couponservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mockcommerce.couponservice.entity.BxGyCouponDetails;

@Repository
public interface BxGyCouponDetailsRepository extends JpaRepository<BxGyCouponDetails, Integer> {

    boolean existsByCouponId(Integer id);
}