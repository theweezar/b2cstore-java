package com.ecom.b2cstore.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ecom.b2cstore.entity.Address;
import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.Customer;
import com.ecom.b2cstore.form.BillingForm;
import com.ecom.b2cstore.form.ShippingForm;
import com.ecom.b2cstore.model.BasketModel;
import com.ecom.b2cstore.model.CustomerModel;
import com.ecom.b2cstore.util.ErrorUtil;
import com.ecom.b2cstore.util.TemplateUtil;

import jakarta.validation.Valid;

@Controller
public class CheckoutShippingController extends BaseController {

    @Autowired
    private TemplateUtil templateUtil;

    public CheckoutShippingController() {
        super();
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
        BillingForm billingForm = new BillingForm(form.getShippingAddress());

        // Copy customer info to billing form
        billingForm.getBillingAddress().setFirstName(form.getFirstName());
        billingForm.getBillingAddress().setLastName(form.getLastName());
        billingForm.getBillingAddress().setEmail(form.getEmail());
        billingForm.getBillingAddress().setPhone(form.getPhone());

        basketService.setBillingAddress(basket, billingForm);
        basketService.save(basket);

        Customer customer = getCurrentCustomer();
        if (customer != null) {
            Address newAddress = new Address(form.getShippingAddress());
            addressService.create(customer, newAddress);
            System.out.println("Created new address: " + newAddress.toString());
        }

        BasketModel basketModel = cartUtil.createModel(basket, true);
        CustomerModel customerModel = getCurrentCustomerModel();

        if (customerModel != null) {
            String savedAddressesHtml = templateUtil.renderTemplate(
                    "checkout/components/savedAddressSelection",
                    Map.of("customerModel", customerModel));
            resMap.put("savedAddressesHtml", savedAddressesHtml);
        }

        resMap.put("basketModel", basketModel);
        resMap.put("customerModel", customerModel);
        resMap.put("success", true);
        return ResponseEntity.ok(resMap);
    }
}
