package com.famipam.security.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String YMD_TIME_FORMAT = "yyyyMMdd";

    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_DATE_FORMAT);
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, DEFAULT_DATE_TIME_FORMAT);
    }

    public static String formatDateTime(Date date, String format) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(format);
        return dateTimeFormat.format(date);
    }

    public static String formatDefaultPassword(Date date) {
        return formatDate(date, YMD_TIME_FORMAT);
    }

    public static Date parseISODate(String date) throws ParseException {
        return parseDate(date, DEFAULT_DATE_FORMAT);
    }
    public static Date parseDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateStr);
    }
}