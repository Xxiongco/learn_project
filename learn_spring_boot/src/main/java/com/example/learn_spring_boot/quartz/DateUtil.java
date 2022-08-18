package com.example.learn_spring_boot.quartz;



import org.omg.CORBA.PUBLIC_MEMBER;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

    public static String formatDateInfo(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
        return format.format(date);
    }

}
