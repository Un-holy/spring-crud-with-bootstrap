package com.fadeevivan.springboot.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NonNull
	@Column(
		name = "first_name",
		nullable = false,
		unique = true
	)
	private String firstName;

	@NonNull
	@Column(
		name = "last_name",
		nullable = false
	)
	private String lastName;

	@NonNull
	@Column(
		name = "age",
		nullable = false

	)
	private byte age;

	@NonNull
	@Column(
		name = "password",
		nullable = false
	)
	private String password;

	public User() {
	}

	public User(@NonNull String firstName, @NonNull String lastName, byte age, @NonNull String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public byte getAge() {
		return age;
	}

	public void setAge(byte age) {
		this.age = age;
	}

	@NonNull
	public String getPassword() {
		return password;
	}

	public void setPassword(@NonNull String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", age=" + age +
				", password='" + password + '\'' +
				'}';
	}
}
