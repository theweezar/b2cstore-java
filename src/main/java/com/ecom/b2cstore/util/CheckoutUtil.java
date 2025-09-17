package com.ecom.b2cstore.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.BasketLineItem;
import com.ecom.b2cstore.entity.LineItem;
import com.ecom.b2cstore.entity.Order;
import com.ecom.b2cstore.entity.Product;
import com.ecom.b2cstore.service.BasketService;
import com.ecom.b2cstore.service.InventoryService;
import com.ecom.b2cstore.service.OrderService;
import com.ecom.b2cstore.service.ProductService;

import lombok.Getter;
import lombok.Setter;

@Component
public class CheckoutUtil {

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BasketService basketService;

    public Map<String, Product> getProductMap(Collection<? extends LineItem> lineItems) {
        Set<String> productIds = lineItems.stream()
                .map(LineItem::getProductId)
                .collect(Collectors.toSet());
        Collection<Product> products = productService.getProductsByIds(productIds);
        Map<String, Product> productMap = new HashMap<>();
        products.forEach(product -> productMap.put(product.getProductId(), product));
        return productMap;
    }

    public Map<String, Integer> getQtyInContainerMap(Collection<? extends LineItem> lineItems) {
        return lineItems.stream().collect(Collectors.toMap(LineItem::getProductId, LineItem::getAts, Integer::sum));
    }

    public boolean validateProducts(Basket basket) {
        Map<String, Product> productMap = getProductMap(basket.getLineItems());
        Map<String, Integer> qtyInBasketMap = getQtyInContainerMap(basket.getLineItems());

        for (BasketLineItem lineItem : basket.getLineItems()) {
            String pid = lineItem.getProductId();
            Product product = productMap.get(pid);
            Integer qtyInBasket = qtyInBasketMap.get(pid);

            if (product == null || !product.isInStock() || product.getAts() < qtyInBasket) {
                return false;
            }
        }

        return true;
    }

    public void deductInventory(Order order) {
        Map<String, Integer> qtyInOrderMap = getQtyInContainerMap(order.getLineItems());
        for (String pid : qtyInOrderMap.keySet()) {
            Integer qty = qtyInOrderMap.get(pid);
            inventoryService.updateInventory(pid, -qty);
        }
    }

    public void restockInventory(Order order) {
        Map<String, Integer> qtyInOrderMap = getQtyInContainerMap(order.getLineItems());
        for (String pid : qtyInOrderMap.keySet()) {
            Integer qty = qtyInOrderMap.get(pid);
            inventoryService.updateInventory(pid, qty);
        }
    }

    public static class PlaceOrderStatus {
        @Getter
        @Setter
        private Order order;
        @Getter
        @Setter
        private boolean success;
        @Getter
        @Setter
        private String error;
        @Getter
        @Setter
        private String redirect;

        public PlaceOrderStatus(Order order, boolean success, String error, String redirect) {
            this.order = order;
            this.success = success;
            this.error = error;
            this.redirect = redirect;
        }
    }

    public PlaceOrderStatus placeOrder(Basket basket) {
        if (!validateProducts(basket)) {
            return new PlaceOrderStatus(null, false, "Product validation failed. Please review your cart.", null);
        }

        Order orderCreated = orderService.createOrder(basket);

        if (orderCreated == null) {
            return new PlaceOrderStatus(null, false, "Order creation failed. Please try again.", null);
        }

        deductInventory(orderCreated);
        basketService.deleteBasket(basket);

        boolean orderPlaced = orderService.placeOrder(orderCreated);

        if (!orderPlaced) {
            orderService.failOrder(orderCreated);
            restockInventory(orderCreated);
            return new PlaceOrderStatus(orderCreated, false, "Order placement failed. Please try again.", null);
        }

        return new PlaceOrderStatus(orderCreated, true, null,
                "/orderconfirmation?orderId=" + orderCreated.getOrderId());
    }
}
