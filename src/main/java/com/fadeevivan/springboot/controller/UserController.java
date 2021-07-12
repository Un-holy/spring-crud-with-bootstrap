package com.fadeevivan.springboot.controller;

import com.fadeevivan.springboot.model.User;
import com.fadeevivan.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("admin/users")
	public String findAll(Model model) {
		model.addAttribute("users", userService.findAll());
		return "admin/users";
	}

	@GetMapping("admin/users/new")
	public String createUserFrom(User user) {
		return "admin/users/new";
	}

	@PostMapping("admin/users/new")
	public String createNewUser(User user) {
		userService.saveUser(user);
		return "redirect:/admin/users";
	}

	@DeleteMapping("admin/users/{id}")
	public String deleteUser(@PathVariable("id") long id) {
		userService.deleteById(id);
		return "redirect:/admin/users";
	}

	@GetMapping("admin/users/{id}/edit")
	public String createEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("user", userService.findById(id));
		return "admin/users/edit";
	}

	@PatchMapping("admin/users/{id}/edit")
	public String editUser(@ModelAttribute("users") User user, @PathVariable("id") long id) {
		userService.saveUser(user);
		return "redirect:/admin/users/";
	}


}
