package com.dominhtuan.util;

import com.dominhtuan.constant.SystemConstant;

public class CheckInputUtil {
    public static boolean isValid(Object object) {
        return (object != null && (!object.equals(SystemConstant.EMPTY_STRING) || Integer.parseInt(object.toString()) != 0));
    }
}
