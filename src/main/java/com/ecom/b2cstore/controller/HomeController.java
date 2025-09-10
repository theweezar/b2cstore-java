package com.ecom.b2cstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends BaseController {
	public HomeController() {
		super();
	}

	@GetMapping("/")
	public String home(Model model) {
		initBaseModel(model);
		model.addAttribute("pageTitle", "Welcome to B2C Store");
		model.addAttribute("pageDescription", "This is a demo B2C Store built with Spring Boot and Thymeleaf.");
		return "home";
	}

}
