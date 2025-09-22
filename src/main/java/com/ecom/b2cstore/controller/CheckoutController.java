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

import com.ecom.b2cstore.entity.Address;
import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.Customer;
import com.ecom.b2cstore.form.BillingForm;
import com.ecom.b2cstore.form.ShippingForm;
import com.ecom.b2cstore.model.AddressModel;
import com.ecom.b2cstore.model.BasketModel;
import com.ecom.b2cstore.model.CustomerModel;
import com.ecom.b2cstore.util.CheckoutUtil;
import com.ecom.b2cstore.util.ErrorUtil;
import jakarta.validation.Valid;

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
        CustomerModel customerModel = getCurrentCustomerModel();
        model.addAttribute("pageTitle", "Checkout");
        model.addAttribute("pageDescription", "Checkout page for your order.");
        model.addAttribute("basketModel", basketModel);
        model.addAttribute("customerModel", customerModel);
        model.addAttribute("isLoggedIn", customerModel != null);
        model.addAttribute("stripeApiKey", env.getProperty("stripe.api.key"));

        // Initialize customer and shipping forms
        ShippingForm shippingForm = new ShippingForm();
        AddressModel billingAddress = basketModel.getBilling().getAddress();
        AddressModel shippingAddress = basketModel.getShipping().getAddress();
        shippingForm.getShippingAddress().copy(shippingAddress);

        if (customerModel != null) {
            // Pre-fill customer info if logged in
            shippingForm.setFirstName(customerModel.getFirstName());
            shippingForm.setLastName(customerModel.getLastName());
            shippingForm.setEmail(customerModel.getEmail());
            shippingForm.setPhone(customerModel.getPhone());
        } else {
            // Pre-fill from billing address if user is guest
            shippingForm.setFirstName(billingAddress.getFirstName());
            shippingForm.setLastName(billingAddress.getLastName());
            shippingForm.setEmail(billingAddress.getEmail());
            shippingForm.setPhone(shippingAddress.getPhone());
        }

        model.addAttribute("shippingForm", shippingForm);
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
            resMap.put("error", ErrorUtil.getBindingResultErrors(result));
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

        Customer customer = getCurrentCustomer();
        if (customer != null) {
            addressService.create(customer, new Address(form.getShippingAddress()));
        }

        BasketModel basketModel = cartUtil.createModel(basket, true);
        resMap.put("basketModel", basketModel);
        resMap.put("customerModel", getCurrentCustomerModel());
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
            resMap.put("error", ErrorUtil.getBindingResultErrors(result));
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
