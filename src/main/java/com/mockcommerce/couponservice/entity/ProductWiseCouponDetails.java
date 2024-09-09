package com.mockcommerce.couponservice.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_wise_coupon_details")
public class ProductWiseCouponDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;    

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "discount")
    private BigDecimal discount;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }


    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    
    
}

