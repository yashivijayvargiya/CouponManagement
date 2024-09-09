package com.mockcommerce.couponservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "bxgy_coupon_buy_products")
public class BxGyCouponBuyProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bxgy_coupon_id")
    private BxGyCouponDetails bxGyCouponDetails;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "quantity")
    private Integer quantity;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BxGyCouponDetails getBxGyCouponDetails() {
        return bxGyCouponDetails;
    }

    public void setBxGyCouponDetails(BxGyCouponDetails bxGyCouponDetails) {
        this.bxGyCouponDetails = bxGyCouponDetails;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

