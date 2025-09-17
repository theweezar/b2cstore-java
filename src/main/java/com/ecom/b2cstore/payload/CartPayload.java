package com.ecom.b2cstore.payload;

import lombok.Getter;
import lombok.Setter;

public class CartPayload {
    @Getter
    @Setter
    private String pid;

    @Getter
    @Setter
    private int quantity;

    @Getter
    @Setter
    private String uuid;

    public CartPayload() {
    }

    public CartPayload(String pid, int quantity, String uuid) {
        this.pid = pid;
        this.quantity = quantity;
        this.uuid = uuid;
    }
}
