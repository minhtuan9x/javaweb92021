package com.dominhtuan.controlleradvice;

import com.dominhtuan.constant.SystemConstant;
import com.dominhtuan.exception.YeuDiemPhucException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<Map<String,Object>> handleArithmeticException(ArithmeticException ae){
        ResponseEntity<Map<String,Object>> mapResponseEntity;
        Map<String,Object> data =new HashMap<>();
        data.put("error",ae.getMessage());
        data.put("message", SystemConstant.FAIL);
        mapResponseEntity = new ResponseEntity<>(data,HttpStatus.INTERNAL_SERVER_ERROR);
        return mapResponseEntity;
    }
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Map<String,Object>> handleNumberFormatException(NumberFormatException e){
        ResponseEntity<Map<String,Object>> mapResponseEntity;
        Map<String,Object> data = new HashMap<>();
        data.put("Error",e.getMessage());
        data.put("Message",SystemConstant.FAIL);
        mapResponseEntity = new ResponseEntity<>(data,HttpStatus.BAD_REQUEST);
        return mapResponseEntity;
    }
    @ExceptionHandler(YeuDiemPhucException.class)
    public ResponseEntity<Map<String,Object>> handleYeuDiemPhucException(YeuDiemPhucException e){
        ResponseEntity<Map<String,Object>> mapResponseEntity;
        Map<String,Object> data = new HashMap<>();
        data.put("Error",e.getMessage());
        data.put("Message",SystemConstant.FAIL);
        mapResponseEntity = new ResponseEntity<>(data,HttpStatus.PAYMENT_REQUIRED);
        return mapResponseEntity;
    }
}
