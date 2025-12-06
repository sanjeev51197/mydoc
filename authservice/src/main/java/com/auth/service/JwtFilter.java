package com.auth.service;

import java.io.IOException;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		   
		    String header= request.getHeader("Authorization");
		    
		    if(header!=null && header.startsWith("Bearer ")) {
		           
		    	   String jwt= header.substring(7);
		    	String username=   jwtService.validateTokenAndRetriveSubject(jwt);
		    	
		    	if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		    	{
		    		  var userDetails=userDetailsService.loadUserByUsername(username);
		    		  var authToken=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		    		  
		    		  authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		    		  SecurityContextHolder.getContext().setAuthentication(authToken);
		    	}
		    	
		        
		    	     
		    }
		    
		    filterChain.doFilter(request, response);
		      
		
	}

}
