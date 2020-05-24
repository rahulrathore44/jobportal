package com.lab.jobportal.exception;

/**
 * rathr1
 * 
 **/

public class JobNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String exceptionMessage;

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public JobNotFoundException(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
	public JobNotFoundException() {
	}

}
