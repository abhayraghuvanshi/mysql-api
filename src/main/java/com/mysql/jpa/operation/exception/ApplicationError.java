package com.mysql.jpa.operation.exception;

public class ApplicationError extends RuntimeException {

	/**
	 * Handle Error if the calculation is not available for any reason
	 */
	private static final long serialVersionUID = 1L;
	private ErrorMessge exceptionMessge ;

	public ApplicationError(ErrorMessge exceptionMessge) {
 
		this.exceptionMessge = exceptionMessge;
	}

	public ErrorMessge getErrorMessage() {
		return exceptionMessge;
	}

	public void setErrorMessage(ErrorMessge exceptionMessge) {
		this.exceptionMessge = exceptionMessge;
	}

	
}
