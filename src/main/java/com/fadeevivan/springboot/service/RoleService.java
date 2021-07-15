package com.fadeevivan.springboot.service;

import com.fadeevivan.springboot.model.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface RoleService {
	Role findRoleById(long id);
}
