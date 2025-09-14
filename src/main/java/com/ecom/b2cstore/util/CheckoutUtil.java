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
import com.ecom.b2cstore.service.InventoryService;
import com.ecom.b2cstore.service.ProductService;

@Component
public class CheckoutUtil {

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;

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
}
