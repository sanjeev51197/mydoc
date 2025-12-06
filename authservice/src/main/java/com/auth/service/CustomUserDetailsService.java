package com.auth.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.entity.User;
import com.auth.exception.ResourceNotFound;
import com.auth.repository.UserRepository;



@Service
public class CustomUserDetailsService implements UserDetailsService {
      @Autowired
      private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      User user= userRepository.findByUsername(username);
      
         if(user==null)
         {
        	 throw new ResourceNotFound("User not found");
         }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
    }


}
