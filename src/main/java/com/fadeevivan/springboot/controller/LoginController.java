package com.fadeevivan.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/")
	public String getLoginPage() {
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String getLoginPage2() {
		return "login";
	}
}
