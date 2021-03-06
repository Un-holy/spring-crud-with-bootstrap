package com.fadeevivan.springboot.service;

import com.fadeevivan.springboot.model.Role;
import com.fadeevivan.springboot.model.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserService userService;

	@Autowired
	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String firstName) throws UsernameNotFoundException {
		// 1 способ
		// User user = userService.findUserByFirstName(firstName);
		// Hibernate.initialize(user.getRoles());

		// 2 способ
		User user = userService.findUserByEmailWithRolesEager(firstName);
		if (Objects.isNull(user)) {
			throw new UsernameNotFoundException(String.format("User %s not found", user));
		}
		return (UserDetails) user;
	}
}
