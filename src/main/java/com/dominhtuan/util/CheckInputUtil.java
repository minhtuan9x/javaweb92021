package com.dominhtuan.util;

import com.dominhtuan.constant.SystemConstant;

public class CheckInputUtil {

    public static boolean isNull(String str) {
        return str == null || str.equals(SystemConstant.EMPTY_STRING);
    }

    public static boolean isNull(Integer num) {
        return num == null || num == 0;
    }

    public static boolean isValid(Object object) {
        return (object != null && (!object.equals(SystemConstant.EMPTY_STRING) || Integer.parseInt(object.toString()) != 0));
    }
}
