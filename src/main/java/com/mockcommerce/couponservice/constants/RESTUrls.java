package com.mockcommerce.couponservice.constants;

public class RESTUrls {
    private RESTUrls() {
        throw new IllegalStateException("RESTUrls class");
    }

    public static final String BASE_URL = "/v1/coupons";
    public static final String APPLICABLE_COUPON = "/applicable-coupons";
    public static final String GET_COUPON_BY_ID ="/{id}";
    public static final String APPLY_COUPON ="/apply-coupon/{id}";

}