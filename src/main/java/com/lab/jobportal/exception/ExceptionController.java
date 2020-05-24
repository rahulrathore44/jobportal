package com.lab.jobportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lab.jobportal.message.CustomMessage;

/**
* rathr1
* 
**/
@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(value = JobNotFoundException.class)
	public ResponseEntity<Object> exception(JobNotFoundException exception) {
		return new ResponseEntity<Object>(new CustomMessage(exception.getExceptionMessage()),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = RuntimeOperationException.class)
	public ResponseEntity<Object> exception(RuntimeOperationException exception) {
		return new ResponseEntity<Object>(new CustomMessage(exception.getExceptionMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}


