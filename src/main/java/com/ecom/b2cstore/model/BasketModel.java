package com.ecom.b2cstore.model;

import com.ecom.b2cstore.entity.Basket;
import lombok.Setter;

public class BasketModel extends ContainerModel {

    @Setter
    private Basket basket;

    public BasketModel() {
        super();
    }

    public BasketModel(Basket basket) {
        super();
        if (basket != null) {
            setBasket(basket);
            setItemCount(basket.getLineItems().size());
            total.setTotalPrice(basket.getTotalPrice());
            total.setTotalTax(0);
            total.setTotalDiscount(0);
            total.setTotalShipping(0);
            copyShippingFrom(basket);
            copyBillingFrom(basket);
        }
    }

    @Override
    protected Basket getContainerInstance() {
        return basket;
    }
}
