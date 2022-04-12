package com.laptrinhjavaweb.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String toDateString(Date date){
        return date!=null? new SimpleDateFormat("dd/MM/yyyy").format(date):new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }
}
