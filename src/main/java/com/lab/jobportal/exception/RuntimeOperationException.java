package com.lab.jobportal.exception;

/**
 * rathr1
 * 
 **/
public class RuntimeOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String exceptionMessage;

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public RuntimeOperationException(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public RuntimeOperationException() {

	}

}
