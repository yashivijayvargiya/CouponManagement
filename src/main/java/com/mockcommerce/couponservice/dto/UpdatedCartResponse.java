package com.mockcommerce.couponservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class UpdatedCartResponse {

    @JsonProperty("updated_cart")
    private UpdatedCart updatedCart;

    public UpdatedCart getUpdatedCart() {
        return updatedCart;
    }

    public void setUpdatedCart(UpdatedCart updatedCart) {
        this.updatedCart = updatedCart;
    }

    public static class UpdatedCart {
        @JsonProperty("items")
        private List<UpdatedCartItem> items;

        @JsonProperty("total_price")
        private BigDecimal totalPrice;

        @JsonProperty("total_discount")
        private BigDecimal totalDiscount;

        @JsonProperty("final_price")
        private BigDecimal finalPrice;

        public List<UpdatedCartItem> getItems() {
            return items;
        }

        public void setItems(List<UpdatedCartItem> items) {
            this.items = items;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public BigDecimal getTotalDiscount() {
            return totalDiscount;
        }

        public void setTotalDiscount(BigDecimal totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public BigDecimal getFinalPrice() {
            return finalPrice;
        }

        public void setFinalPrice(BigDecimal finalPrice) {
            this.finalPrice = finalPrice;
        }

        // Getters and Setters...
    }

    public static class UpdatedCartItem {
        @JsonProperty("product_id")
        private Integer productId;

        @JsonProperty("quantity")
        private Integer quantity;

        @JsonProperty("price")
        private BigDecimal price;

        @JsonProperty("total_discount")
        private BigDecimal totalDiscount;

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

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getTotalDiscount() {
            return totalDiscount;
        }

        public void setTotalDiscount(BigDecimal totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        // Getters and Setters...
    }
}

