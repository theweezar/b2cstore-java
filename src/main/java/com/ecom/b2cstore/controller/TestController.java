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
import com.ecom.b2cstore.form.ShippingForm;
import jakarta.validation.Valid;

@Controller
public class TestController extends BaseController {
    @GetMapping("/test/form")
    public String testForm(Model model) {
        model.addAttribute("shippingForm", new ShippingForm());
        return "test/form";
    }

    @PostMapping("/test/submit")
    public ResponseEntity<?> submitForm(@Valid @ModelAttribute ShippingForm form,
            BindingResult result) {
        Map<String, Object> resMap = new HashMap<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error -> resMap.put(error.getField(),
                    error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(resMap);
        }
        resMap.put("submittedForm", form);
        resMap.put("shipping", form.getShippingAddress());
        return ResponseEntity.ok(resMap);
    }
}
