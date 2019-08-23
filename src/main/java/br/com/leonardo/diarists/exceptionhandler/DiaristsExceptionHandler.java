package br.com.leonardo.diarists.exceptionhandler;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DiaristsExceptionHandler {
	
	@ExceptionHandler({NoSuchElementException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public void handleNoSuchElementException() {
	}
	
}
