package com.ecom.b2cstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecom.b2cstore.model.CustomerModel;

@Controller
public class AccountController extends BaseController {
    @GetMapping("/account")
    public String account(Model model) {
        initBaseModel(model);
        CustomerModel customerModel = getCurrentCustomerModel();
        model.addAttribute("customerModel", customerModel);
        model.addAttribute("pageTitle", "My Account");
        model.addAttribute("pageDescription", "Manage your account details and view order history.");
        return "account";
    }
}
