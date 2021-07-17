package com.fadeevivan.springboot.controller;

import com.fadeevivan.springboot.model.User;
import com.fadeevivan.springboot.service.RoleService;
import com.fadeevivan.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;

@Controller
public class AdminController {
	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping("admin/users")
	public String findAll(Model model, @AuthenticationPrincipal User authUser) {
		Collection<String> roles = new HashSet<>();
		authUser.getAuthorities().forEach(a -> roles.add(a.getAuthority().substring(5)));
		model.addAttribute("users", userService.findAll());
		model.addAttribute("authUser", authUser);
		model.addAttribute("roles", roles);
		return "admin/users";
	}

	@GetMapping("admin/users/new")
	public String createUserFrom(User user, Model model) {
		model.addAttribute("roleAdmin", roleService.findRoleById(1));
		model.addAttribute("roleUser", roleService.findRoleById(2));
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
		return "redirect:/admin/users";
	}
}
