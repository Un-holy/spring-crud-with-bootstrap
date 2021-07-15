package com.fadeevivan.springboot.model;


import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NonNull
	@Column(
		name = "role_name",
		nullable = false
	)
	private String roleName;

	public Role() {
	}

	public Role(@NonNull String roleName) {
		this.roleName = roleName;
	}

	@NonNull
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(@NonNull String role) {
		this.roleName = role;
	}

	@Override
	public String toString() {
		return roleName.substring(5);
	}

	@Override
	public String getAuthority() {
		return roleName;
	}
}
