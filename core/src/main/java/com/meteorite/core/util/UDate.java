package com.meteorite.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
        return LocalDate.parse(date.replace(' ', 'T'), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static String dateToString(LocalDate date) {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .append(DateTimeFormatter.ISO_LOCAL_TIME);

        return date.format(builder.toFormatter());
    }
}
