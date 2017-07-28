package com.charles.common.date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static com.charles.common.date.DatePatterns.DATE;

public final class DateHelper {

    public static ImmutablePair<Date, Date> lastHourRange() throws ParseException {
        Date currentHour = DateUtils.truncate(new Date(), Calendar.HOUR);
        Date hourStart = DateUtils.addHours(currentHour, -1);
        Date hourEnd = DateUtils.addSeconds(currentHour, -1);
        return new ImmutablePair<>(hourStart, hourEnd);
    }

    public static ImmutablePair<Date, Date> lastWeekRange(String dateStr) throws ParseException {
        Objects.requireNonNull(dateStr);
        if (StringUtils.isEmpty(dateStr)) {
            return lastWeekRange();
        }
        return lastWeekRange(DateUtils.parseDate(dateStr, DATE));
    }

    public static ImmutablePair<Date, Date> lastWeekRange() throws ParseException {
        return lastWeekRange(new Date());
    }

    public static ImmutablePair<Date, Date> lastWeekRange(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1 - calendar.get(Calendar.DAY_OF_WEEK) - 7);
        Date firstDay = calendar.getTime();
        return new ImmutablePair<>(firstDay, DateUtils.addDays(firstDay, 6));
    }

    public static ImmutablePair<Date, Date> lastMonthRange(String dateStr) throws ParseException {
        Objects.requireNonNull(dateStr);
        if (StringUtils.isEmpty(dateStr)) {
            return lastMonthRange();
        }
        return lastMonthRange(DateUtils.parseDate(dateStr, DATE));
    }

    public static ImmutablePair<Date, Date> lastMonthRange() throws ParseException {
        return lastMonthRange(new Date());
    }

    public static ImmutablePair<Date, Date> lastMonthRange(Date date) throws ParseException {
        Date currentMonthFirstDay = DateUtils.truncate(new Date(), Calendar.MONTH);
        Date firstDay = DateUtils.addMonths(currentMonthFirstDay, -1);
        Date lastDay = DateUtils.addDays(currentMonthFirstDay, -1);
        return new ImmutablePair<>(firstDay, lastDay);
    }

    public static Date getFirstDayOfWeek(String yearWeek) {
        Objects.requireNonNull(yearWeek);
        Assert.isTrue(yearWeek.length() == 6);
        String year = yearWeek.substring(0, 4);
        String week = yearWeek.substring(4, 6);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.valueOf(year));
        calendar.set(Calendar.WEEK_OF_YEAR, Integer.valueOf(week));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//设置该周第一天为星期天
        // String firstDayOfWeek = DateFormatUtils.format(cal.getTime(), DatePatterns.DATE);
        // System.out.println(yearMonth + "的第一天是：" + firstDayOfWeek);
        return calendar.getTime();
    }
}
