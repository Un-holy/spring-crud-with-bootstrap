package com.fadeevivan.springboot.service;

import com.fadeevivan.springboot.model.Role;
import com.fadeevivan.springboot.model.User;
import com.fadeevivan.springboot.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	private final RoleRepository roleRepository;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Role findRoleById(long id) {
		return roleRepository.getById(id);
	}

	@Override
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}
}
