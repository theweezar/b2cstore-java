package com.ecom.b2cstore.controller;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ecom.b2cstore.entity.Category;
import com.ecom.b2cstore.entity.CategoryAssignment;

@Controller
public class SearchController extends BaseController {
    public SearchController() {
        super();
    }

    @GetMapping("/search/{id}")
    public String show(Model model, @PathVariable("id") String categoryId) {
        initBaseModel(model);

        Category category = categoryService.getCategoryById(categoryId);
        Set<CategoryAssignment> assignments = new HashSet<>();

        if (category != null) {
            assignments = category.getAssignments();
        }

        model.addAttribute("category", category);
        model.addAttribute("assignments", assignments);
        model.addAttribute("pageTitle", "Search Products");
        model.addAttribute("pageDescription", "Search for products in the B2C Store.");

        return "search";
    }
}
