package com.dominhtuan.controlleradvice;

import com.dominhtuan.exception.MyException;
import com.dominhtuan.util.HandleExceptionMethodUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

	@ExceptionHandler(ArithmeticException.class)
	public ResponseEntity<Map<String, Object>> handleArithmeticException(ArithmeticException e) {
		return HandleExceptionMethodUtil.handleMethod(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<Map<String, Object>> handleNumberFormatException(NumberFormatException e) {
		return HandleExceptionMethodUtil.handleMethod(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MyException.class)
	public ResponseEntity<Map<String, Object>> handleMyException(MyException e) {
		return HandleExceptionMethodUtil.handleMethod(e, HttpStatus.PAYMENT_REQUIRED);
	}
}
