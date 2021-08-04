package com.fadeevivan.springboot.service;

import com.fadeevivan.springboot.model.User;
import com.fadeevivan.springboot.repository.RoleRepository;
import com.fadeevivan.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User findById(long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User saveUser(User user) {
		if (user.getPassword().equals("")) {
			user.setPassword(userRepository.findById(user.getId()).get().getPassword());
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		userRepository.save(user);
		return user;
	}

	@Override
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findUserByFirstName(String firstName) {
		return userRepository.findUserByFirstName(firstName);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	@Override
	public User findUserByEmailWithRolesEager(String email) {
		return userRepository.findUserByEmailWithRolesEager(email);
	}
}
