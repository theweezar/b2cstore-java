package com.ecom.b2cstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ecom.b2cstore.entity.Order;
import com.ecom.b2cstore.model.OrderModel;

@Controller
public class OrderController extends BaseController {

    @GetMapping("/orderconfirmation")
    public String showOrderConfirmation(@RequestParam String orderId) {
        // initBaseModel(model);
        Order order = orderService.getOrderById(Long.parseLong(orderId));
        if (order == null) {
            return "redirect:/";
        }
        OrderModel orderModel = orderUtil.createModel(order, true);
        request.setAttribute("orderModel", orderModel);
        return "confirmation";
    }

}
