package com.ecom.b2cstore.payload;

import lombok.Getter;
import lombok.Setter;

public class AddToCartPayload {
    @Getter
    @Setter
    private String pid;

    @Getter
    @Setter
    private int quantity;

    public AddToCartPayload() {
    }

    public AddToCartPayload(String pid, int quantity) {
        this.pid = pid;
        this.quantity = quantity;
    }
}
