package com.fadeevivan.springboot.service;

import com.fadeevivan.springboot.model.User;

import java.util.List;

public interface UserService {
	User findById(long id);
	List<User> findAll();
	User saveUser(User user);
	void deleteById(long id);
	User findUserByFirstName(String firstName);
	User findUserByEmail(String email);
	User findUserByEmailWithRolesEager(String email);
}
