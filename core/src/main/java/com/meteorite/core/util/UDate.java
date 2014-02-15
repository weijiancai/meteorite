package com.meteorite.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class UDate {
    public static String dateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date toDate(String date) throws ParseException {
        return toDate(date, "yyyy-MM-dd HH:ss:mm");
    }

    public static Date toDate(String date, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(date);
    }
}
