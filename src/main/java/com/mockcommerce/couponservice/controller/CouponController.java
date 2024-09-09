package com.mockcommerce.couponservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mockcommerce.couponservice.constants.RESTUrls;
import com.mockcommerce.couponservice.dto.ApplicableCouponResponse;
import com.mockcommerce.couponservice.dto.CartRequest;
import com.mockcommerce.couponservice.dto.CouponRequest;
import com.mockcommerce.couponservice.dto.UpdatedCartResponse;
import com.mockcommerce.couponservice.entity.Coupon;
import com.mockcommerce.couponservice.service.CouponService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping(RESTUrls.BASE_URL)
public class CouponController {
    static Logger logger = LogManager.getLogger(CouponController.class);
    @Autowired
    private CouponService couponService;

    @PostMapping
    public ResponseEntity<Void> createCoupon(@RequestBody CouponRequest couponRequest) {
        logger.info("Creating new coupon with details: {}", couponRequest);
        couponService.createCoupon(couponRequest);
        logger.info("Coupon created successfully.");
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupon() {
        logger.info("Fetching all coupons.");
        List<Coupon> coupons = couponService.getAllCoupon();
        logger.info("Found {} coupons.", coupons.size());
        return ResponseEntity.ok(coupons);
    }

    @GetMapping(RESTUrls.GET_COUPON_BY_ID)
    public ResponseEntity<Coupon> getCouponById(@PathVariable Integer id) {
        logger.info("Fetching coupon with ID: {}", id);
        Coupon coupon = couponService.getCouponById(id);
        if (coupon != null) {
            logger.info("Coupon found: {}", coupon);
            return ResponseEntity.ok(coupon);
        } else {
            logger.warn("Coupon with ID {} not found.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(RESTUrls.GET_COUPON_BY_ID)
    public ResponseEntity<Coupon> updateCouponById(@PathVariable Integer id, @RequestBody Coupon updatedCoupon) {
        logger.info("Updating coupon with ID: {}", id);
        Coupon coupon = couponService.updateCouponById(id, updatedCoupon);
        if (coupon != null) {
            logger.info("Coupon updated successfully: {}", coupon);
            return ResponseEntity.ok(coupon);
        } else {
            logger.warn("Coupon with ID {} not found for update.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(RESTUrls.GET_COUPON_BY_ID)
    public ResponseEntity<Void> deleteCouponById(@PathVariable Integer id) {
        logger.info("Deleting coupon with ID: {}", id);
        boolean isDeleted = couponService.deleteCouponById(id);
        if (isDeleted) {
            logger.info("Coupon with ID {} deleted successfully.", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Coupon with ID {} not found for deletion.", id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @PostMapping(RESTUrls.APPLICABLE_COUPON)
    public ResponseEntity<ApplicableCouponResponse> getApplicableCoupons(@RequestBody CartRequest cartRequest) {
        logger.info("Fetching applicable coupons for cart: {}", cartRequest.getCart());
        List<ApplicableCouponResponse.CouponDetails> applicableCoupons = couponService
                .getApplicableCoupons(cartRequest.getCart());
        ApplicableCouponResponse response = new ApplicableCouponResponse();
        response.setApplicableCoupons(applicableCoupons);

        logger.info("Found {} applicable coupons.", applicableCoupons.size());
        return ResponseEntity.ok(response);
    }

    @PostMapping(RESTUrls.APPLY_COUPON)
    public ResponseEntity<UpdatedCartResponse> applyCoupon(@PathVariable("id") Long couponId,
            @RequestBody CartRequest cartRequest) {
        logger.info("Applying coupon with ID: {} to cart: {}", couponId, cartRequest.getCart());
        UpdatedCartResponse response = couponService.applyCoupon(couponId, cartRequest.getCart());
        logger.info("Coupon applied successfully. Updated cart: {}", response);
        return ResponseEntity.ok(response);
    }

}