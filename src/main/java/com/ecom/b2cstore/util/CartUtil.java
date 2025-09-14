package com.ecom.b2cstore.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.Product;
import com.ecom.b2cstore.model.CartModel;

@Component
public class CartUtil {
    @Autowired
    private CheckoutUtil checkoutUtil;

    public CartModel createModel(Basket basket) {
        if (basket == null) {
            return new CartModel();
        }
        return new CartModel(basket);
    }

    public CartModel createModel(Basket basket, boolean includeItems) {
        if (basket == null) {
            return new CartModel();
        }
        CartModel cartModel = new CartModel(basket);
        if (basket.getLineItems().isEmpty() || !includeItems) {
            return cartModel;
        }
        Map<String, Product> productMap = checkoutUtil.getProductMap(basket.getLineItems());
        if (includeItems) {
            cartModel.setItems(cartModel.createItemList(productMap));
        }
        return cartModel;
    }
}
