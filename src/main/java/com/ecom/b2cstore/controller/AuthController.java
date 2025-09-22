package com.ecom.b2cstore.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ecom.b2cstore.entity.Customer;
import com.ecom.b2cstore.form.CustomerForm;
import com.ecom.b2cstore.service.CustomerService;
import com.ecom.b2cstore.util.ErrorUtil;
import jakarta.validation.Valid;

@Controller
public class AuthController extends BaseController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLogin(Model model,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String error) {
        initBaseModel(model);
        model.addAttribute("username", username);
        if (error != null) {
            model.addAttribute("loginError", "Invalid username or password");
        }
        return "login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        initBaseModel(model);
        model.addAttribute("customerForm", new CustomerForm());
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @ModelAttribute CustomerForm form, BindingResult result) {
        Map<String, Object> resMap = new HashMap<>();

        if (result.hasErrors()) {
            resMap.put("error", ErrorUtil.getBindingResultErrors(result));
            return ResponseEntity.badRequest().body(resMap);
        }
        if (!form.isPasswordConfirmed()) {
            resMap.put("confirmPassword", "Passwords do not match");
            return ResponseEntity.badRequest().body(resMap);
        }
        if (customerService.existsByUsername(form.getUsername())) {
            resMap.put("username", "Username is already taken");
            return ResponseEntity.badRequest().body(resMap);
        }
        if (customerService.existsByEmail(form.getEmail())) {
            resMap.put("email", "Email is already registered");
            return ResponseEntity.badRequest().body(resMap);
        }

        // Proceed with registration
        Customer customer = new Customer(form);
        customer.setPassword(passwordEncoder.encode(form.getPassword()));
        customer = customerService.registerCustomer(customer);

        if (customer == null) {
            resMap.put("error", "Registration failed. Please try again.");
            return ResponseEntity.badRequest().body(resMap);
        }

        resMap.put("success", true);
        resMap.put("redirect", "/login?username=" + form.getUsername());

        return ResponseEntity.ok(resMap);
    }
}
