package com.laptrinhjavaweb.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BuildingControllerAdvice {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String,Object>> handleNullPointerException(NullPointerException e){
        Map<String,Object> results = new HashMap<>();
        results.put("Status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        results.put("value","Loi khong duoc de null");
        results.put("value",e.getMessage());
        return new ResponseEntity<>(results, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
