package com.fadeevivan.springboot.controller;

import com.fadeevivan.springboot.service.RoleService;
import com.fadeevivan.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.HashSet;

@Controller
public class LoginController {
	private final UserService userService;
	private final RoleService roleService;

	public static final String REST_URL = "/admin/users";

	@Autowired
	public LoginController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping("/")
	public String getLoginPage() {
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String getLoginPage2() {
		return "login";
	}

	@GetMapping("/admin")
	public String  getAdminPage(Model model, @AuthenticationPrincipal UserDetails u) {
		Collection<String> roles = new HashSet<>();
		u.getAuthorities().forEach(a -> roles.add(a.getAuthority().substring(5)));
		model.addAttribute("userInfo", userService.findUserByEmail(u.getUsername()));
		model.addAttribute("authUser", u);
		model.addAttribute("authRoles", roles);
		return "admin/index";
	}
}
