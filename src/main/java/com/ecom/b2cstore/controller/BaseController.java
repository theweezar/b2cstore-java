package com.ecom.b2cstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.Customer;
import com.ecom.b2cstore.model.BasketModel;
import com.ecom.b2cstore.service.BasketService;
import com.ecom.b2cstore.service.CategoryService;
import com.ecom.b2cstore.service.OrderService;
import com.ecom.b2cstore.service.ProductService;
import com.ecom.b2cstore.util.CartUtil;
import com.ecom.b2cstore.util.CheckoutUtil;
import com.ecom.b2cstore.util.OrderUtil;
import com.ecom.b2cstore.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

    @Autowired
    protected OrderService orderService;

    @Autowired
    protected CheckoutUtil checkoutUtil;

    @Autowired
    protected CartUtil cartUtil;

    @Autowired
    protected OrderUtil orderUtil;
    
    @Autowired
    protected Environment env;

    protected void initBaseModel(Model model) {
        Basket basket = getCurrentBasket();
        BasketModel basketModel = cartUtil.createModel(basket);
        Customer customer = getCurrentCustomer();
        String username = (customer != null) ? customer.getUsername() : null;
        boolean loggedIn = isLoggedIn();

        System.out.println("Current username in BaseController constructor: " + username);

        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("basketModel", basketModel);
    }

    protected String getGuestUUID() {
        return (String) request.getAttribute("guestUUID");
    }

    protected Customer getCurrentCustomer() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (Customer) session.getAttribute("customer");
        }
        return null;
    }

    protected Basket getCurrentBasket() {
        if (isLoggedIn()) {
            Customer customer = getCurrentCustomer();
            return customer != null ? basketService.getBasketByCustomerId(customer.getCustomerId()) : null;
        } else {
            String guestUUID = getGuestUUID();
            return basketService.getBasketByGuestUUID(guestUUID);
        }
    }

    protected boolean isLoggedIn() {
        return SecurityUtil.isLoggedIn();
    }

}
