package com.dominhtuan.util;

import com.dominhtuan.constant.SystemConstant;

public class ParseValidateUtil {
    public static Object parseValidate(Object str) {
        if (str == null || str.equals(SystemConstant.EMPTY_STRING))
            return null;
        if(str.toString().matches("[0-9]{1,}")){
            return Integer.parseInt(str.toString());
        }
        return str.toString();
    }
}
