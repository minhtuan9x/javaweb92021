package com.dominhtuan.util;

import com.dominhtuan.constant.SystemConstant;

public class ParseValidateUtil {
    public static Object parseValidate(Object obj) {
        if (obj == null || obj.toString().equals(SystemConstant.EMPTY_STRING))
            return null;
        if(obj.toString().matches("[0-9]{1,}")){
            return Integer.parseInt(obj.toString());
        }
        return obj.toString();
    }
}
