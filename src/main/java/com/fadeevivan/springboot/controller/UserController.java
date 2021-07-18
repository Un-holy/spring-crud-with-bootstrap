package com.fadeevivan.springboot.controller;

import com.fadeevivan.springboot.model.User;
import com.fadeevivan.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.HashSet;

@Controller
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping()
	public String redirect(@AuthenticationPrincipal User authUser) {
		long id = userService.findUserByEmail(authUser.getUsername()).getId();
		return "redirect:/user/" + id;
	}

	@GetMapping("/{id}")
	public String showUserPage(@PathVariable("id") long id, Model model, @AuthenticationPrincipal User authUser) {
		Collection<String> roles = new HashSet<>();
		authUser.getAuthorities().forEach(a -> roles.add(a.getAuthority().substring(5)));
		model.addAttribute("user", userService.findById(id));
		model.addAttribute("authUser", authUser);
		model.addAttribute("roles", roles);
		return "user/show";
	}
}
