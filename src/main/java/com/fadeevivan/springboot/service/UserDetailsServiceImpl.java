package com.fadeevivan.springboot.service;

import com.fadeevivan.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String firstName) throws UsernameNotFoundException {
		User user = userService.findUserByFirstName(firstName);
		if (Objects.isNull(user)) {
			throw new UsernameNotFoundException(String.format("User %s not found", user));
		}
		return (UserDetails) user;
	}
}
