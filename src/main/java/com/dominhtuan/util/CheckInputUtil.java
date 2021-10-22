package com.dominhtuan.util;

public class CheckInputUtil {

    public boolean isNull(String str){
        if(str == null || str.equals("")){
            return true;
        }

        return false;
    }

    public boolean isNull(Integer num){
        if(num==null||num==0){
            return true;
        }
        return false;
    }
}
