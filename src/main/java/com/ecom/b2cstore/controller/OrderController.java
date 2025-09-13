package com.ecom.b2cstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController extends BaseController {

    @GetMapping("/orderconfirmation")
    public String showOrderConfirmation(@RequestParam String orderId) {
        return "orderConfirmation";
    }

}
