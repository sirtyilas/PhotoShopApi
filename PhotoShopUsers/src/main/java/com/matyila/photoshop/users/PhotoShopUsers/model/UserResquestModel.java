package com.matyila.photoshop.users.PhotoShopUsers.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserResquestModel {
	
	@NotNull(message="First name cannot be null")
	@Size(min = 2 ,message="First name must not be less then two characters")
	private String firstName;
	
	@NotNull(message="Last name cannot be null")
	@Size(min = 2 ,message="Last name must not be less then two characters")
	private String lastName;
	
	@NotNull(message="Password cannot be null")
	@Size(min = 2 ,max=16,message="Password must not be less then two characters")
	private String password;

	@NotNull(message="Email cannot be null")
	@Email
	private String email;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String name) {
		this.firstName = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}
