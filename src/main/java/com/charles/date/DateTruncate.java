package com.charles.date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTruncate {

    public static void truncateDate(Date d, int unit) {
        Date date = null;
        switch (unit) {
            case 1:
                date = DateUtils.truncate(d, Calendar.MONTH);
                break;
            case 2:
                date = DateUtils.truncate(d, Calendar.WEEK_OF_YEAR);
                break;
            case 3:
                date = DateUtils.truncate(d, Calendar.DAY_OF_MONTH);
                break;
            case 4:
                date = DateUtils.truncate(d, Calendar.HOUR_OF_DAY);
                break;
        }
        System.out.println(DateFormatUtils.format(date, DatePatterns.DATETIME));
    }

    public static void main(String[] args) throws ParseException {
//        truncateDate(DateUtils.parseDate("2016-11-07 10:11:23", DatePatterns.DATETIME), 1);
//        truncateDate(DateUtils.parseDate("2016-11-07 10:11:23", DatePatterns.DATETIME), 2);
//        truncateDate(DateUtils.parseDate("2016-11-07 10:11:23", DatePatterns.DATETIME), 3);
//        truncateDate(DateUtils.parseDate("2016-11-07 10:11:23", DatePatterns.DATETIME), 4);
        Timestamp date = new Timestamp(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.HOUR_OF_DAY)));//2009-08-04 16:00:00
        System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.MINUTE)));//2009-08-04 16:23:00
        System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.SECOND))); //2009-08-04 16:23:14

        System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.DAY_OF_MONTH)));//2009-08-04 00:00:00
        System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.MONTH))); //2009-08-01 00:00:00
        System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.YEAR))); //2009-01-01 00:00:00

        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        System.out.println(cal.getTime());
    }

}
