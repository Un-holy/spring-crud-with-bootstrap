package com.fadeevivan.springboot.repository;

import com.fadeevivan.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByFirstName(String firstName);

	// 2 способ
	@Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.firstName = (:firstName)")
	User findUserByEmailWithRolesEager(@Param("firstName") String firstName);
}
