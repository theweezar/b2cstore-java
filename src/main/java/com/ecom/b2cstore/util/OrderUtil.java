package com.ecom.b2cstore.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ecom.b2cstore.entity.Order;
import com.ecom.b2cstore.entity.Product;
import com.ecom.b2cstore.model.OrderModel;

@Component
public class OrderUtil {
    @Autowired
    private CheckoutUtil checkoutUtil;

    public OrderModel createModel(Order order) {
        if (order == null) {
            return new OrderModel();
        }
        return new OrderModel(order);
    }

    public OrderModel createModel(Order order, boolean includeItems) {
        if (order == null) {
            return new OrderModel();
        }
        OrderModel orderModel = new OrderModel(order);
        if (order.getLineItems().isEmpty() || !includeItems) {
            return orderModel;
        }
        Map<String, Product> productMap = checkoutUtil.getProductMap(order.getLineItems());
        if (includeItems) {
            orderModel.setItems(orderModel.createItemList(productMap));
        }
        return orderModel;
    }
}
