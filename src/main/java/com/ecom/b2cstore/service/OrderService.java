package com.ecom.b2cstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.Order;
import com.ecom.b2cstore.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Basket basket) {
        try {
            Order order = basket.convertToOrder();
            orderRepository.save(order);
            return order;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean placeOrder(Order order) {
        try {
            order.setStatus(Order.STATUS_OPEN);
            order.setConfirmationStatus(Order.CONFIRMATION_STATUS_CONFIRMED);
            orderRepository.save(order);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void failOrder(Order order) {
        if (order != null) {
            order.setStatus(Order.STATUS_FAILED);
            orderRepository.save(order);
        }
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
