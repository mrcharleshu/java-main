package com.charles.common.date;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import static com.charles.utils.LineSeparators.hyphenSeparator;

public class DateTruncate {

    public static void main(String[] args) {
        hyphenSeparator("date utils truncate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date();
        System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.YEAR))); //2009-01-01 00:00:00
        System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.MONTH))); //2009-08-01 00:00:00
        System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.DAY_OF_MONTH)));//2009-08-04 00:00:00
        System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.HOUR_OF_DAY)));//2009-08-04 16:00:00
        System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.MINUTE)));//2009-08-04 16:23:00
        System.out.println(dateFormat.format(DateUtils.truncate(date, Calendar.SECOND))); //2009-08-04 16:23:14
        hyphenSeparator("jdk8 date truncate");
        System.out.println(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:SS")));
        hyphenSeparator("calendar");
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        System.out.println(cal.getTime());
    }
}