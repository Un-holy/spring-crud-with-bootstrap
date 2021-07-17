package com.fadeevivan.springboot.repository;

import com.fadeevivan.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByFirstName(String firstName);
	User findUserByEmail(String email);
}
