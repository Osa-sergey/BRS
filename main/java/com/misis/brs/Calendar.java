package com.misis.brs;

import java.util.TimeZone;

public class Calendar {
    public static final java.util.Calendar calendar = java.util.Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    public static long dateToMillis(int year,int month,int day){
        calendar.clear();
        calendar.set(year, month - 1, day);
        return calendar.getTimeInMillis();
    }
}
