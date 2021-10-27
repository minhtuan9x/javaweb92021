package com.dominhtuan.util;

import com.dominhtuan.constant.SystemConstant;

public class CheckInputUtil {

    public static boolean isNull(String str) {
        if (str == null || str.equals(SystemConstant.EMPTY_STRING)) {
            return true;
        }
        return false;
    }

    public static boolean isNull(Integer num) {
        if (num == null || num == 0) {
            return true;
        }
        return false;
    }
    public static boolean isValid(Object object) {
        return null != object && (!SystemConstant.EMPTY_STRING.equals(object) || 0 != Integer.parseInt(object.toString()));
    }
}
