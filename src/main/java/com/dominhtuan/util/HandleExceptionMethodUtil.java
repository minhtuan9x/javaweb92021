package com.dominhtuan.util;

import com.dominhtuan.constant.SystemConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class HandleExceptionMethodUtil {
    public ResponseEntity<Map<String,Object>> handleMethod(Exception e, HttpStatus httpStatus){
        ResponseEntity<Map<String,Object>> mapResponseEntity;
        Map<String,Object> data = new HashMap<>();
        data.put(SystemConstant.STATUS,httpStatus.value());
        data.put(SystemConstant.ERROR,e.getMessage());
        data.put(SystemConstant.MESSAGE,SystemConstant.FAIL);
        mapResponseEntity = new ResponseEntity<>(data,httpStatus);
        return mapResponseEntity;
    }
}
