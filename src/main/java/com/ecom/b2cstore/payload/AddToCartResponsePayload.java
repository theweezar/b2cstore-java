package com.ecom.b2cstore.payload;

import com.ecom.b2cstore.model.CartModel;

import lombok.Getter;
import lombok.Setter;

public class AddToCartResponsePayload {
    @Getter
    @Setter
    private boolean status;

    @Getter
    @Setter
    private String productStatus;

    @Getter
    @Setter
    private CartModel cartModel;

    public AddToCartResponsePayload() {
    }

    public AddToCartResponsePayload(boolean status, String productStatus) {
        this.status = status;
        this.productStatus = productStatus;
    }
}
