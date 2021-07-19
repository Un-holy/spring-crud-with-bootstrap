package com.fadeevivan.springboot.controller;

import com.fadeevivan.springboot.model.User;
import com.fadeevivan.springboot.service.RoleService;
import com.fadeevivan.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

	@GetMapping("admin")
	public String findAll(Model model, @AuthenticationPrincipal UserDetails u) {
		//TODO закэшировать роли в userService
		Collection<String> roles = new HashSet<>();
		u.getAuthorities().forEach(a -> roles.add(a.getAuthority().substring(5)));
		model.addAttribute("users", userService.findAll());
		model.addAttribute("authUser", u);
		model.addAttribute("roles", roles);
		return "admin/users";
	}

	@GetMapping("admin/new")
	public String createUserFrom(User user, Model model) {
		model.addAttribute("roleAdmin", roleService.findRoleById(1L));
		model.addAttribute("roleUser", roleService.findRoleById(2L));
		return "admin/new";
	}

	@PostMapping("admin/new")
	public String createNewUser(User user) {
		userService.saveUser(user);
		return "redirect:/admin/";
	}

	@DeleteMapping("admin/{id}")
	public String deleteUser(@PathVariable("id") long id) {
		userService.deleteById(id);
		return "redirect:/admin/";
	}

	@GetMapping("admin/{id}/edit")
	public String createEditForm(@PathVariable("id") long id, Model model,
								 @AuthenticationPrincipal UserDetails u) {
		// TODO как исправить дублирование кода?
		Collection<String> roles = new HashSet<>();
		u.getAuthorities().forEach(a -> roles.add(a.getAuthority().substring(5)));
		model.addAttribute("user", userService.findById(id));
		model.addAttribute("authUser", u);
		model.addAttribute("roles", roles);
		return "admin/edit";
	}

	@PatchMapping("admin/{id}/edit")
	public String editUser(@ModelAttribute("users") User user, @PathVariable("id") long id) {
		userService.saveUser(user);
		return "redirect:/admin";
	}
}
