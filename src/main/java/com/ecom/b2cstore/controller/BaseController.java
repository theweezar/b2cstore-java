package com.ecom.b2cstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.model.CartModel;
import com.ecom.b2cstore.service.BasketService;
import com.ecom.b2cstore.service.CategoryService;
import com.ecom.b2cstore.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public abstract class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected CategoryService categoryService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected BasketService basketService;

    protected void initBaseModel(Model model) {
        String guestUUID = getGuestUUID();
        Basket currentBasket = basketService.getBasketByGuestUUID(guestUUID);
        CartModel cartModel = new CartModel(currentBasket);

        System.out.println("Guest UUID in BaseController constructor: " + guestUUID);

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("guestUUID", guestUUID);
        model.addAttribute("cartModel", cartModel);
    }

    protected String getGuestUUID() {
        return (String) request.getAttribute("guestUUID");
    }
}
