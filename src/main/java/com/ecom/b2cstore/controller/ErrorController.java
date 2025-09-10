package com.ecom.b2cstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorController {
    @GetMapping("/error")
    public String showError(
            @RequestParam(name = "message", required = false, defaultValue = "An unexpected error occurred.") String message) {
        return "error";
    }

}
