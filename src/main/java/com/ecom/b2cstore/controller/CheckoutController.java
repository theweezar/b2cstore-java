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
import com.ecom.b2cstore.entity.Product;
import com.ecom.b2cstore.form.PaymentForm;
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
        String guestUUID = getGuestUUID();
        Basket basket = basketService.getBasketByGuestUUID(guestUUID);
        Map<String, Product> productMap = null;
        CartModel cartModel = null;

        // If no basket exists, redirect to home page
        if (basket == null) {
            return "redirect:/";
        }

        productMap = basketService.getProductMap(basket);
        cartModel = new CartModel(basket);
        cartModel.setItems(cartModel.createItemList(productMap));

        model.addAttribute("cartModel", cartModel);
        return "checkout";
    }

    @PostMapping(value = "/submitshipping", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> submitShipping(
            @Valid @ModelAttribute ShippingForm form,
            BindingResult result) {
        // String guestUUID = getGuestUUID();
        // Basket basket = basketService.getBasketByGuestUUID(guestUUID);
        Map<String, String> resMap = new HashMap<>();
        resMap.put("success", "true");
        return ResponseEntity.ok(resMap);

        // if (basket == null) {
        // resMap.put("redirect", "/");
        // return ResponseEntity.ok(resMap);
        // }

        // if (result.hasErrors()) {
        // result.getFieldErrors().forEach(error -> resMap.put(error.getField(),
        // error.getDefaultMessage()));
        // return ResponseEntity.badRequest().body(resMap);
        // }

        // // If valid, return success
        // basketService.saveShippingForm(basket, form);

        // resMap.put("success", "true");
        // return ResponseEntity.ok(resMap);
    }

    @PostMapping(value = "/submitpayment", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> submitPayment(
    // @Valid @ModelAttribute PaymentForm form,
    // BindingResult result
    ) {
        Map<String, String> resMap = new HashMap<>();
        resMap.put("success", "true");
        return ResponseEntity.ok(resMap);
    }

    @PostMapping(value = "/placeorder")
    public ResponseEntity<?> placeOrder(
    // @Valid @ModelAttribute PaymentForm form,
    // BindingResult result
    ) {
        Map<String, String> resMap = new HashMap<>();
        resMap.put("success", "true");
        return ResponseEntity.ok(resMap);
    }

}
