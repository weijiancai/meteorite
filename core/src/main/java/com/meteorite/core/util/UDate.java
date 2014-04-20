package com.meteorite.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class UDate {
    public static DateTimeFormatter DEFAULT_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");

    public static String dateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date toDate(String date) {
        try {
            return toDate(date, "yyyy-MM-dd HH:ss:mm");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date toDate(String date, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(date);
    }

    public static LocalDate toLocalDate(String date) {
        if (date.length() > 19) {
            date = date.substring(0, 19);
        }
        return LocalDate.parse(date, DEFAULT_FORMAT);
    }

    public static String dateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
