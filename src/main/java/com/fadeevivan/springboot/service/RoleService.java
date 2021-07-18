package com.fadeevivan.springboot.service;

import com.fadeevivan.springboot.model.Role;
import com.fadeevivan.springboot.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


public interface RoleService {
	Role findRoleById(long id);
}
