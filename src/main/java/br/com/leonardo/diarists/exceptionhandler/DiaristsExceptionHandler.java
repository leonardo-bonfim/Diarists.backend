package br.com.leonardo.diarists.exceptionhandler;

import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.leonardo.diarists.response.Response;

@ControllerAdvice
public class DiaristsExceptionHandler {
	
	@ExceptionHandler({NoSuchElementException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public void handleNoSuchElementException() {
	}
	
	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<Response<Object>> handleConstraintViolationException(ConstraintViolationException ex) {
		
		Response<Object> response = new Response<>();
		ex.getConstraintViolations().forEach(a -> response.getErrors().add(a.getMessage()));
		
		return ResponseEntity.badRequest().body(response);
	}
	
}
