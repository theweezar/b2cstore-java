package com.ecom.b2cstore.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.Order;
import com.ecom.b2cstore.form.ShippingForm;
import com.ecom.b2cstore.model.CartModel;
import jakarta.validation.Valid;

@Controller
public class CheckoutController extends BaseController {

    public CheckoutController() {
        super();
    }

    @GetMapping("/checkout")
    public String start(Model model) {
        Basket basket = getCurrentBasket();

        // If no basket exists, redirect to home page
        if (basket == null) {
            return "redirect:/";
        }
        CartModel cartModel = cartUtil.createModel(basket, true);

        model.addAttribute("cartModel", cartModel);
        return "checkout";
    }

    @PostMapping(value = "/submitshipping", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> submitShipping(
            @Valid @ModelAttribute ShippingForm form,
            BindingResult result) {
        Basket basket = getCurrentBasket();
        Map<String, Object> resMap = new HashMap<>();

        if (basket == null) {
            resMap.put("redirect", "/");
            return ResponseEntity.ok(resMap);
        }

        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error -> resMap.put(error.getField(),
                    error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(resMap);
        }

        basketService.saveShippingBillingForm(basket, form);
        CartModel cartModel = cartUtil.createModel(basket, true);
        resMap.put("cartModel", cartModel);
        resMap.put("success", true);
        return ResponseEntity.ok(resMap);
    }

    @PostMapping(value = "/submitpayment", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> submitPayment() {
        Map<String, Object> resMap = new HashMap<>();
        Basket basket = getCurrentBasket();
        if (basket == null) {
            resMap.put("redirect", "/");
            return ResponseEntity.ok(resMap);
        }
        CartModel cartModel = cartUtil.createModel(basket, true);
        resMap.put("cartModel", cartModel);
        resMap.put("success", true);
        return ResponseEntity.ok(resMap);
    }

    @PostMapping(value = "/placeorder")
    public ResponseEntity<?> placeOrder() {
        Map<String, String> resMap = new HashMap<>();
        Basket basket = getCurrentBasket();

        if (basket == null) {
            resMap.put("redirect", "/");
            return ResponseEntity.ok(resMap);
        }

        if (!checkoutUtil.validateProducts(basket)) {
            resMap.put("error", "Product validation failed. Please review your cart.");
            return ResponseEntity.badRequest().body(resMap);
        }

        Order orderCreated = orderService.createOrder(basket);

        if (orderCreated == null) {
            resMap.put("error", "Order creation failed. Please try again.");
            return ResponseEntity.badRequest().body(resMap);
        }

        checkoutUtil.deductInventory(orderCreated);
        basketService.deleteBasket(basket);

        boolean orderPlaced = orderService.placeOrder(orderCreated);

        if (!orderPlaced) {
            orderService.failOrder(orderCreated);
            checkoutUtil.restockInventory(orderCreated);
            resMap.put("error", "Order placement failed. Please try again.");
            return ResponseEntity.badRequest().body(resMap);
        }

        resMap.put("success", "true");
        resMap.put("redirect", "/orderconfirmation?orderId=" + orderCreated.getOrderId());

        return ResponseEntity.ok(resMap);
    }

}
