package com.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class UserDto {
	      
	    private long id;
	    @NotBlank(message = "it is mandatory")
	    @Size(min = 2,message = "Atleast 2 characters to be given")
	    private String name;
	    @Email
	    private String email;
	    @NotBlank(message = "required username ")
	    private String username;
	    
	    private String password;
	    
	    private String role;

		public String getName() {
			return name;
			
			
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}
	    
	    
	    
	    
	    
	    

}
