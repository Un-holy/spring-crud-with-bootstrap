package com.fadeevivan.springboot.controller;

import com.fadeevivan.springboot.model.Role;
import com.fadeevivan.springboot.model.User;
import com.fadeevivan.springboot.service.RoleService;
import com.fadeevivan.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@GetMapping
	public List<User> getAll() {
		return userService.findAll();
	}

	@GetMapping("/{id}")
	public User get(@PathVariable long id) {
		return userService.findById(id);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public User create(@RequestBody User user) {
		user.setRoles(roleService.findAllUserRoles(user));
		User created = userService.saveUser(user);
		return created;
	}

	@PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody User user,@PathVariable long id) {
		user.setRoles(roleService.findAllUserRoles(user));
		userService.saveUser(user);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable long id) {
		userService.deleteById(id);
	}
}
