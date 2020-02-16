package com.mysql.jpa.operation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationException extends ResponseEntityExceptionHandler{

	/**
	 * Handle Error if the service is not available for any reason
	 */
	@ExceptionHandler(value = { ApplicationError.class })
	public final ResponseEntity<ErrorMessge> handleAllExceptions(ApplicationError ex, WebRequest request) {
		ErrorMessge errorObj = ex.getErrorMessage();
		return new ResponseEntity<>(errorObj, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	
}
