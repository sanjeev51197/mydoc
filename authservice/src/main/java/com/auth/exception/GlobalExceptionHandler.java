package com.auth.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.auth.dto.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler  {
	
	
	  @ExceptionHandler(ResourceNotFound.class)
	  public ResponseEntity<ErrorDetails> exceptionHandle(
			  ResourceNotFound r,
			  WebRequest request)
	  {
		  ErrorDetails details=new ErrorDetails(r.getMessage(),new Date(),request.getDescription(false));
		  return new ResponseEntity<ErrorDetails>(details,HttpStatus.OK);
	  }
        
}
