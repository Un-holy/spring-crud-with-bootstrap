package com.fadeevivan.springboot.controller;

import com.fadeevivan.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@GetMapping()
	public String redirect() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(userService.findUserByFirstName(name));
		long id = userService.findUserByFirstName(name).getId();
		System.out.println(id);

		return "redirect:/user/" + id;
	}

	@GetMapping("/{id}")
	public String showUserPage(@PathVariable("id") long id, Model model) {
		model.addAttribute("user", userService.findById(id));
		return "user/show";
	}
}
