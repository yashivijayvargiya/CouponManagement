package com.mockcommerce.couponservice.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Cart {
    @JsonProperty("items")
    private List<CartItem> items;

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}


