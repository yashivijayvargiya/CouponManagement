package com.mockcommerce.couponservice.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "bxgy_coupon_details")
public class BxGyCouponDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "repetition_limit")
    private Integer repetitionLimit;
    @OneToMany(mappedBy = "bxGyCouponDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BxGyCouponBuyProducts> buyProducts;

    @OneToMany(mappedBy = "bxGyCouponDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BxGyCouponGetProducts> getProducts;
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

    public Integer getRepetitionLimit() {
        return repetitionLimit;
    }

    public void setRepetitionLimit(Integer repetitionLimit) {
        this.repetitionLimit = repetitionLimit;
    }

    public List<BxGyCouponBuyProducts> getBuyProducts() {
        return buyProducts;
    }

    public void setBuyProducts(List<BxGyCouponBuyProducts> buyProducts) {
        this.buyProducts = buyProducts;
    }

    public List<BxGyCouponGetProducts> getGetProducts() {
        return getProducts;
    }

    public void setGetProducts(List<BxGyCouponGetProducts> getProducts) {
        this.getProducts = getProducts;
    }
}