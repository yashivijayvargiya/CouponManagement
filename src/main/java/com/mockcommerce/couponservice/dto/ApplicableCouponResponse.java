package com.mockcommerce.couponservice.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ApplicableCouponResponse {

    private List<CouponDetails> applicable_coupons;

    public ApplicableCouponResponse() {
        this.applicable_coupons = new ArrayList<>();
    }

    public List<CouponDetails> getApplicableCoupons() {
        return applicable_coupons;
    }

    public void setApplicableCoupons(List<CouponDetails> applicableCoupons) {
        this.applicable_coupons = applicableCoupons;
    }

    // Inner class to represent coupon details
    public static class CouponDetails {
        private Integer coupon_id;
        private String type;
        private BigDecimal discount;

        public CouponDetails(Integer coupon_id, String type, BigDecimal discount) {
            this.coupon_id = coupon_id;
            this.type = type;
            this.discount = discount;
        }

        // Getters and setters
        public Integer getCouponId() {
            return coupon_id;
        }

        public void setCouponId(Integer couponId) {
            this.coupon_id = couponId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public BigDecimal getDiscount() {
            return discount;
        }

        public void setDiscount(BigDecimal discount) {
            this.discount = discount;
        }
    }
}
