package com.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	User findByUsername(String username);
	User findByEmail(String email);

}
