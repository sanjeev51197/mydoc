package com.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.dto.LoginDto;
import com.auth.dto.UserDto;
import com.auth.entity.User;
import com.auth.response.ApiResponse;
import com.auth.service.AuthService;
import com.auth.service.JwtFilter;
import com.auth.service.JwtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	     @Autowired
	     private AuthenticationManager authManager;
	     
	     @Autowired
	     private AuthService authService;
	     
	     @Autowired
	     private JwtFilter jwtFilter;
	     
	     @Autowired
	     private JwtService jwtService;
	     
	     
	     @PostMapping("/register-admin")
	     public ResponseEntity<ApiResponse<String>> registerAdmin(
	             @Valid @RequestBody UserDto userDto,
	             BindingResult result) {

	         // If validation fails
	         if (result.hasErrors()) {
	             ApiResponse<String> errorResponse = new ApiResponse<>();
	             errorResponse.setMessage(result.getFieldError().toString());
	             errorResponse.setStatus(400);
	             errorResponse.setData(null);

	             return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	         }

	         // Set role
	         userDto.setRole("ROLE_ADMIN");

	         // Call service to register
	         authService.registerUser(userDto);

	         // Success response
	         ApiResponse<String> response = new ApiResponse<>();
	         response.setMessage("Registration Successful");
	         response.setStatus(201);
	         response.setData("User has been added");

	         return new ResponseEntity<>(response, HttpStatus.CREATED);
	     }
	     
	     
	     @PostMapping("/register-user")
	     public ResponseEntity<ApiResponse<String>> registerUser(@Valid @RequestBody UserDto userDto,BindingResult result)
	     {
	    	 
	    	 if(result.hasErrors())
	    	 {
	    		 ApiResponse<String> errorResponse=new ApiResponse<>();
	    		 errorResponse.setMessage(result.getFieldError().toString());
	    		 errorResponse.setStatus(400);
	    		 errorResponse.setData(null);
	    		 return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	    	 }
	    	 
	    	 //set role
	    	 userDto.setRole("ROLE_USER");
	    	 
	    	 
	    	 //register user
	    	 authService.registerUser(userDto);
	    	 ApiResponse<String> response=new ApiResponse<>();
	    	  response.setMessage("Registration Successful");
	    	  response.setStatus(201);
	    	  response.setData("User has been added");
	    	 
	    	 return new ResponseEntity<ApiResponse<String>>(response,HttpStatus.CREATED);
	     }

	   
	      ////////////////=============LOGIN=====================///////////////
	     
	     @PostMapping("/login")
	     public ResponseEntity<ApiResponse<String>> loginCheck(@RequestBody LoginDto loginDto)
	     {
	    	 ApiResponse<String> response=new ApiResponse<>();
	    	 
	    	 UsernamePasswordAuthenticationToken token =new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
	    	 
	    	 try {
				
	    		Authentication authenticate= authManager.authenticate(token);
	    		 
	    		 if(authenticate.isAuthenticated())
	    			 
	    		 {
	    			 String jwt=jwtService.generateJwtToken(loginDto.getUsername(), authenticate.getAuthorities().iterator().next().getAuthority());
	    			 response.setMessage("login successful");
	    			 response.setStatus(201);
	    			 response.setData(jwt);
	    			 return new ResponseEntity<ApiResponse<String>>(response,HttpStatusCode.valueOf(response.getStatus()));
	    		 }
	     
			} catch (Exception e) {

                     e.printStackTrace();
                  
                  
			} 
	    	 
	    	 response.setMessage("login denied");
  			 response.setStatus(500);
  			 response.setData("Un-authorized access");
  			 return new ResponseEntity<ApiResponse<String>>(response,HttpStatusCode.valueOf(response.getStatus()));
  		 }
	     
	     @GetMapping("/list")
	     public ResponseEntity<ApiResponse<List<User>>> list() {

	         List<User> list = authService.listOfUser();

	         ApiResponse<List<User>> response = new ApiResponse<>();
	         response.setStatus(200);
	         response.setMessage("Users fetched successfully");
	         response.setData(list);

	         return new ResponseEntity<>(response, HttpStatus.OK);
	     }
	     
	   //http://localhost:8080/api/v1/auth?pageNo=0&pageSize=3  
	     @GetMapping
	     public ResponseEntity<ApiResponse<List<User>>> fetchAll(
	             @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo ,
	             @RequestParam(value = "pageSize",defaultValue = "3",required = false)int pageSize
	     )
	     {
	         List<User> users=   authService.getUserList(pageNo,pageSize);
	         ApiResponse<List<User>> response = new ApiResponse<>();
	         response.setMessage("Book Deleted Successfully");
	         response.setStatus(200);
	         response.setData(users);
	         return new ResponseEntity<>(response, HttpStatus.OK);

	     }
	        
	     

}
