package com.dominhtuan.util;

import com.dominhtuan.constant.SystemConstant;

public class ParseValidateUtil {
	public static Object parseValidate(Object obj) {
		if (obj == null || obj.toString().equals(SystemConstant.EMPTY_STRING))
			return null;
		if(obj instanceof String)
			return obj.toString();
		else {
			try {
				return Integer.parseInt(obj.toString());
			}catch (Exception e){
				return 0;
			}
		}
	}
}
