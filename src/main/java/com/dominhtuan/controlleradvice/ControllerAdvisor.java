package com.dominhtuan.controlleradvice;

import com.dominhtuan.constant.SystemConstant;
import com.dominhtuan.exception.FieldNullException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<Map<String,Object>> handleArithmeticException(ArithmeticException ae, WebRequest request){
        Map<String,Object> data =new HashMap<>();
        data.put("error",ae.getMessage());
        data.put("message", SystemConstant.FAIL);
        return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FieldNullException.class)
    public ResponseEntity<Map<String,Object>> handleFieldNotNullException(FieldNullException ae, WebRequest request){
        Map<String,Object> data =new HashMap<>();
        data.put("error",ae.getMessage());
        data.put("message", SystemConstant.FAIL);
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
