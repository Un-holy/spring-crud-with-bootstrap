package com.fadeevivan.springboot.service;

import com.fadeevivan.springboot.model.User;
import com.fadeevivan.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User findById(long id) {
		return userRepository.getById(id);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public void deleteById(long id) {
		userRepository.deleteById(id);
	}
}
