package com.ecom.b2cstore.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.Product;
import com.ecom.b2cstore.model.BasketModel;

@Component
public class CartUtil {
    @Autowired
    private CheckoutUtil checkoutUtil;

    public BasketModel createModel(Basket basket) {
        if (basket == null) {
            return new BasketModel();
        }
        return new BasketModel(basket);
    }

    public BasketModel createModel(Basket basket, boolean includeItems) {
        if (basket == null) {
            return new BasketModel();
        }
        BasketModel basketModel = new BasketModel(basket);
        if (basket.getLineItems().isEmpty() || !includeItems) {
            return basketModel;
        }
        Map<String, Product> productMap = checkoutUtil.getProductMap(basket.getLineItems());
        if (includeItems) {
            basketModel.setItems(basketModel.createItemList(productMap));
        }
        return basketModel;
    }
}
