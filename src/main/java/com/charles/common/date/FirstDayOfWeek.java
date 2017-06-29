package com.charles.common.date;

import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FirstDayOfWeek {

    public static String getFirstDayOfWeek(String yearMonth) {
        Assert.isTrue(yearMonth.length() == 6);
        String year = yearMonth.substring(0, 4);
        String month = yearMonth.substring(4, 6);
        System.out.println("Year【" + year + "】month【" + month + "】");
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, Integer.valueOf(year));
        //设置周
        cal.set(Calendar.WEEK_OF_YEAR, Integer.valueOf(month));
        //设置该周第一天为星期一
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("M-d");
        String firstDayOfWeek = sdf.format(cal.getTime());

        return firstDayOfWeek;
    }

    public static void main(String[] args) {
        String firstDay = getFirstDayOfWeek("201422");
        System.out.println("2014年第22周的第一天是：" + firstDay);
    }

}