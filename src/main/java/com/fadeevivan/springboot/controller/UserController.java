package com.fadeevivan.springboot.controller;

import com.fadeevivan.springboot.model.User;
import com.fadeevivan.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	public String showUserPage(Model model, @AuthenticationPrincipal UserDetails u, User user) {
		System.out.println("user controller invoked");
		Collection<String> roles = new HashSet<>();
		u.getAuthorities().forEach(a -> roles.add(a.getAuthority().substring(5)));
		model.addAttribute("userInfo", userService.findUserByEmail(u.getUsername()));
		model.addAttribute("authUser", u);
		model.addAttribute("authRoles", roles);
		return "user/user";
	}
}
