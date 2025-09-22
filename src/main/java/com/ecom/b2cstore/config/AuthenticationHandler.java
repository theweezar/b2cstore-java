package com.ecom.b2cstore.config;

import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.ecom.b2cstore.entity.Customer;
import com.ecom.b2cstore.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthenticationHandler implements AuthenticationSuccessHandler {
    private CustomerService customerService;

    public AuthenticationHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {
        String username = authentication.getName();
        Customer customer = customerService.findByUsername(username);
        HttpSession session = request.getSession(false);

        Customer existingCustomer = (Customer) session.getAttribute("customer");
        if (session != null && existingCustomer == null) {
            session.setAttribute("customer", customer);
            session.setAttribute("username", customer.getUsername());
        }

        // Merge basket if exists in session

        response.sendRedirect("/");
    }
}
