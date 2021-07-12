package com.fadeevivan.springboot.repository;

import com.fadeevivan.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
