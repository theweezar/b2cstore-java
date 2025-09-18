package com.ecom.b2cstore.payload;

import com.ecom.b2cstore.model.BasketModel;

import lombok.Getter;
import lombok.Setter;

public class AddToCartResponsePayload {
    @Getter
    @Setter
    private boolean status;

    @Getter
    @Setter
    private int productStatus;

    @Getter
    @Setter
    private BasketModel basketModel;

    public AddToCartResponsePayload() {
    }

    public AddToCartResponsePayload(boolean status, int productStatus) {
        this.status = status;
        this.productStatus = productStatus;
    }
}
