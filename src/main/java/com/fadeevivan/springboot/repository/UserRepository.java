package com.fadeevivan.springboot.repository;

import com.fadeevivan.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByFirstName(String firstName);
	User findUserByEmail(String email);

	// 2 способ
	@Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = (:email)")
	User findUserByEmailWithRolesEager(@Param("email") String email);
}
