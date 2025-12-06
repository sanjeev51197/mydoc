package com.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/user")
public class UserController {
	
	@GetMapping("/dashboard")
	public String getDashboard()
	{
		return "This is user dashboard";
	}
}