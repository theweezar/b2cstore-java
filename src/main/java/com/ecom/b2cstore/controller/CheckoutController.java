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
import com.ecom.b2cstore.form.BillingForm;
import com.ecom.b2cstore.form.ShippingForm;
import com.ecom.b2cstore.model.BasketModel;
import com.ecom.b2cstore.util.CheckoutUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CheckoutController extends BaseController {

    public CheckoutController() {
        super();
    }

    @GetMapping("/checkout")
    public String start(Model model) {
        Basket basket = getCurrentBasket();

        if (basket == null) {
            return "redirect:/";
        }
        BasketModel basketModel = cartUtil.createModel(basket, true);
        model.addAttribute("pageTitle", "Checkout");
        model.addAttribute("pageDescription", "Checkout page for your order.");
        model.addAttribute("basketModel", basketModel);
        model.addAttribute("stripeApiKey", env.getProperty("stripe.api.key"));

        // Initialize forms
        model.addAttribute("shippingForm", new ShippingForm());
        model.addAttribute("billingForm", new BillingForm());

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

        basketService.setCustomerInfo(basket, form);
        basketService.setShippingAddress(basket, form);

        // Set billing address same as shipping address initially
        BillingForm billingForm = new BillingForm();
        billingForm.setBillingAddress(form.getShippingAddress());

        // Copy customer info to billing form
        billingForm.getBillingAddress().setFirstName(form.getFirstName());
        billingForm.getBillingAddress().setLastName(form.getLastName());
        billingForm.getBillingAddress().setEmail(form.getEmail());
        billingForm.getBillingAddress().setPhone(form.getPhone());

        basketService.setBillingAddress(basket, billingForm);
        basketService.save(basket);

        BasketModel basketModel = cartUtil.createModel(basket, true);
        resMap.put("basketModel", basketModel);
        resMap.put("success", true);
        return ResponseEntity.ok(resMap);
    }

    @PostMapping("/updatebilling")
    public ResponseEntity<?> updateBilling(@Valid @ModelAttribute BillingForm form,
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

        basketService.setBillingAddress(basket, form);
        basketService.save(basket);

        BasketModel basketModel = cartUtil.createModel(basket, true);
        resMap.put("basketModel", basketModel);
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
        BasketModel basketModel = cartUtil.createModel(basket, true);
        resMap.put("basketModel", basketModel);
        resMap.put("success", true);
        return ResponseEntity.ok(resMap);
    }

    @PostMapping(value = "/placeorder")
    public ResponseEntity<?> placeOrder() {
        Map<String, Object> resMap = new HashMap<>();
        Basket basket = getCurrentBasket();

        if (basket == null) {
            resMap.put("redirect", "/");
            return ResponseEntity.ok(resMap);
        }

        CheckoutUtil.PlaceOrderStatus status = checkoutUtil.placeOrder(basket);

        if (!status.isSuccess()) {
            if (status.getError() != null) {
                resMap.put("error", status.getError());
            }
            if (status.getRedirect() != null) {
                resMap.put("redirect", status.getRedirect());
            }
            return ResponseEntity.badRequest().body(resMap);
        }

        resMap.put("success", true);
        resMap.put("redirect", status.getRedirect());
        return ResponseEntity.ok(resMap);
    }

}
