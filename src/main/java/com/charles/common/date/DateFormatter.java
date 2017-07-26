package com.charles.common.date;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SimpleDateFormat is not thread-safe
 */
public class DateFormatter {
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat FORMAT_MONTH = new SimpleDateFormat("M月");
    private static final SimpleDateFormat FORMAT_WEEK = new SimpleDateFormat("M-d当周");
    private static final SimpleDateFormat FORMAT_DAY = new SimpleDateFormat("M-d");
    private static final SimpleDateFormat FORMAT_HOUR = new SimpleDateFormat("M-d H:00");
    private static final SimpleDateFormat FORMAT_MINUTE = new SimpleDateFormat("M-d H:mm");
    private static final SimpleDateFormat PARSE_MINUTE = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws ParseException {
        Date date = DateUtils.parseDate("2016-07-15 02:34:56", "yyyy-MM-dd HH:mm:ss");
        System.out.println(FORMAT_DATE.format(date));
        System.out.println(FORMAT_MONTH.format(date));
        System.out.println(FORMAT_WEEK.format(date));
        System.out.println(FORMAT_DAY.format(date));
        System.out.println(FORMAT_HOUR.format(date));
        System.out.println(FORMAT_MINUTE.format(date));
        System.out.println(PARSE_MINUTE.parse("2017-07-13"));
    }
}
