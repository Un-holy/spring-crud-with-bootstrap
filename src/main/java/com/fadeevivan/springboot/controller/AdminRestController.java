package com.fadeevivan.springboot.controller;

import com.fadeevivan.springboot.model.Role;
import com.fadeevivan.springboot.model.User;
import com.fadeevivan.springboot.service.RoleService;
import com.fadeevivan.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {
	private final UserService userService;
	private final RoleService roleService;

	public static final String REST_URL = "/admin/users";

	@Autowired
	public AdminRestController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

//	@GetMapping("admin")
//	public String findAll(Model model, @AuthenticationPrincipal UserDetails u, User user) {
//		Collection<String> roles = new HashSet<>();
//		u.getAuthorities().forEach(a -> roles.add(a.getAuthority().substring(5)));
//		model.addAttribute("users", userService.findAll());
//		model.addAttribute("userInfo", userService.findUserByEmail(u.getUsername()));
//		model.addAttribute("authUser", u);
//		model.addAttribute("authRoles", roles);
//		model.addAttribute("roleAdmin", roleService.findRoleById(1L));
//		model.addAttribute("roleUser", roleService.findRoleById(2L));
//		model.addAttribute("allRoles", roleService.findAllRoles());
//		return "admin/users";
//	}
//
//	@PostMapping("admin/new")
//	public String createNewUser(User user) {
//		userService.saveUser(user);
//		return "redirect:/admin";
//	}
//
//	@PutMapping("admin/update")
//	public String editUser(@ModelAttribute("user") User user) {
//		userService.saveUser(user);
//		return "redirect:/admin";
//	}
//
//	@DeleteMapping("admin/delete")
//	public String deleteUser(@ModelAttribute("user") User user) {
//		userService.deleteById(user.getId());
//		return "redirect:/admin";
//	}

	@GetMapping
	public List<User> getAll() {
		System.out.println("Method getAll from REST invoked");
		return userService.findAll();
	}

	@GetMapping("/{id}")
	public User get(@PathVariable long id) {
		return userService.findById(id);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> create(@RequestBody User user) {
		User created = userService.saveUser(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(REST_URL + "/{id}")
				.buildAndExpand(created.getId()).toUri();
		return ResponseEntity.created(uri).body(created);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody User user,@PathVariable long id) {
		//TODO delete
		user.setRoles(roleService.findAllUserRoles(user));
		System.out.println(user.getRoles());
		userService.saveUser(user);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable long id) {
		userService.deleteById(id);
	}

	@GetMapping("/by")
	public User getByEmail(@RequestParam String email) {
		return userService.findUserByEmail(email);
	}
}
