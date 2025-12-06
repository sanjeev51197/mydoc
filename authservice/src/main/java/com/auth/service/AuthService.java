package com.auth.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.dto.UserDto;
import com.auth.entity.User;
import com.auth.exception.ResourceNotFound;
import com.auth.repository.UserRepository;
import com.auth.response.ApiResponse;

@Service
public class AuthService {
	
	  @Autowired
	  private UserRepository userRepository;
	  
	  @Autowired
	  private PasswordEncoder encoder;
	  
	  
	  public ApiResponse<String> registerUser(UserDto userDto)
	  {
		  ApiResponse<String> response=new ApiResponse<>();
		  if(userRepository.existsByUsername(userDto.getUsername()))
		  {
			  response.setMessage("Registration Failed");
			  response.setStatus(500);
			  response.setData("Username already taken");
			  return response;
		  }
		  
		  if(userRepository.existsByEmail(userDto.getEmail()))
		  {
			  response.setMessage("Registration Failed");
			  response.setStatus(500);
			  response.setData("Email already taken");
			  return response;
		  }
		  
		  User user=new User();
		  BeanUtils.copyProperties(userDto, user);
		  user.setPassword(encoder.encode(userDto.getPassword()));
		  
		  userRepository.save(user);

		  response.setMessage("Registration Successful");
		  response.setStatus(500);
		  response.setData("User has been added");
		  return response;
		  
	  }
	  
	  
	  public java.util.List<User> listOfUser()
	  {
		java.util.List<User> list=  userRepository.findAll();
		return list;
	  }
	  
	  
	  public User getUserById(long id)
	  {
		User user=  userRepository.findById(id).get();
		if(user==null)
		{
			throw new ResourceNotFound("Not found");
		}
		return user;
	  }
	  
	  
	  public void updateUser(UserDto userDto)
	  {
		 User user=    userRepository.findById(userDto.getId()).get(); 
		 if(user ==null)
		 {
			 throw new ResourceNotFound("Not found");
		 }
		 
		    user.setName(userDto.getName());
		    user.setEmail(userDto.getEmail());
		    user.setUsername(userDto.getUsername());
		    
		   if(userDto.getPassword()!=null && !userDto.getPassword().isEmpty())
		    {
		    	user.setPassword(encoder.encode(userDto.getPassword()));
		    }
		    
		    user.setRole(userDto.getRole());
		    
		    userRepository.save(user);
		    
	  }
	  
	  
	  public void deleteUser(long id)
	  {
		  User user=    userRepository.findById(id).get(); 
			 if(user ==null)
			 {
				 throw new ResourceNotFound("Not found");
			 }
			 userRepository.deleteById(id);
	  }


	  public java.util.List<User> getUserList(int pageNo, int pageSize) {
		
		  {
		        Pageable pageable= PageRequest.of(pageNo,pageSize);
		       Page<User> page= userRepository.findAll(pageable);
		          List<User> books=    page.getContent();
		          return books;
		    }
		
	  
	  
	  
	  
	   
	  
	  }
}
