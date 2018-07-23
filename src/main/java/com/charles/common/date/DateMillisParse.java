package com.charles.common.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.TimeZone;

public class DateMillisParse {

    public static void main(String[] args) {
        // ### LocalDateTime转毫秒 1
        Long millis1 = LocalDateTime.now()
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        System.out.println(millis1);

        // ### LocalDateTime转毫秒 2
        Long millis2 = LocalDateTime.now()
                .toInstant(ZoneOffset.of("+8"))
                .toEpochMilli();
        System.out.println(millis2);

        // ### 毫秒转LocalDateTime 1
        LocalDateTime datetime1 = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(1526353091760L),
                TimeZone.getDefault().toZoneId());
        System.out.println(datetime1);

        // ### 毫秒转LocalDateTime 2
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(1505209914826L);
        LocalDateTime dateTime2 = LocalDateTime.ofInstant(
                calendar.toInstant(),
                ZoneId.systemDefault());
        System.out.println(dateTime2);
    }
}
