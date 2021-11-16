package org.ulearn.login.loginservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<CustomErrorMessage> handleException(CustomException exc){
		
		CustomErrorMessage customeErrorMessage = new CustomErrorMessage(HttpStatus.BAD_REQUEST.value(),
				exc.getMessage(),
				System.currentTimeMillis());
		return new ResponseEntity<>(customeErrorMessage, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<CustomErrorMessage> handleException(Exception exc){
		
		CustomErrorMessage customeErrorMessage = new CustomErrorMessage(HttpStatus.BAD_REQUEST.value(),
				exc.getMessage(),
				System.currentTimeMillis());
		return new ResponseEntity<>(customeErrorMessage, HttpStatus.BAD_REQUEST);
		
	}

}
