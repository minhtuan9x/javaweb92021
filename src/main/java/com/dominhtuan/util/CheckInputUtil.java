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

    //    public static boolean isNull(Object object) {
//        if (object == null)
//            return true;
//        if (object instanceof String) {
//            if (object.equals(SystemConstant.EMPTY_STRING))
//                return true;
//        }
//        if (object instanceof Integer) {
//            if (Integer.parseInt(object.toString()) == 0)
//                return true;
//        }
//        return false;
//    }
    public static boolean isValid(Object object) {
        return null == object && (SystemConstant.EMPTY_STRING.equals(object) || 0 == Integer.parseInt(object.toString()));
    }
}
