package com.ecom.b2cstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecom.b2cstore.repository.ProductRepository;

@Controller
public class CheckoutController {

    private final ProductRepository productRepository;

    public CheckoutController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/checkout")
    public String start(
            @CookieValue(name = "productsAddedToCart", defaultValue = "") String productsAddedToCart) {

        return "checkout";
    }
}
