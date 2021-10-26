package com.dominhtuan.controlleradvice;

import com.dominhtuan.exception.YeuDiemPhucException;
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
        ResponseEntity<Map<String, Object>> mapResponseEntity = HandleExceptionMethodUtil.handleMethod(e, HttpStatus.INTERNAL_SERVER_ERROR);
        return mapResponseEntity;
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Map<String, Object>> handleNumberFormatException(NumberFormatException e) {
        ResponseEntity<Map<String, Object>> mapResponseEntity = HandleExceptionMethodUtil.handleMethod(e, HttpStatus.BAD_REQUEST);
        return mapResponseEntity;
    }

    @ExceptionHandler(YeuDiemPhucException.class)
    public ResponseEntity<Map<String, Object>> handleYeuDiemPhucException(YeuDiemPhucException e) {
        ResponseEntity<Map<String, Object>> mapResponseEntity = HandleExceptionMethodUtil.handleMethod(e, HttpStatus.PAYMENT_REQUIRED);
        return mapResponseEntity;
    }
}
