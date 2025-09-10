package com.ecom.b2cstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController extends BaseController {
    public ProductController() {
        super();
    }

    @GetMapping("/product/{id}")
    public String show(Model model, @PathVariable("id") String productId) {
        initBaseModel(model);

        // Placeholder for product retrieval logic
        // Product product = productService.getProductById(productId);
        // model.addAttribute("product", product);

        model.addAttribute("pageTitle", "Product Details");
        model.addAttribute("pageDescription", "Detailed view of the selected product.");

        return "product";
    }
}
