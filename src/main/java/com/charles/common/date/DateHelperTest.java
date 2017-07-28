package com.charles.common.date;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

import static com.charles.common.date.DateHelper.*;

public class DateHelperTest {
    private static final Logger logger = LoggerFactory.getLogger(DateHelperTest.class);

    public static void main(String[] args) throws ParseException {
        Date firstDayOfWeek = getFirstDayOfWeek("201730");
        logger.info("firstDayOfWeek = {}", firstDayOfWeek);
        System.out.println(firstDayOfWeek);
        ImmutablePair<Date, Date> lastHourRange = lastHourRange();
        logger.info("lastHourRange : hour start = {},hour end = {}", lastHourRange.getLeft(), lastHourRange.getRight());
        ImmutablePair<Date, Date> lastWeekRange = lastWeekRange("2017-07-28");
        logger.info("lastWeekRange : first day = {},last day = {}", lastWeekRange.getLeft(), lastWeekRange.getRight());
        ImmutablePair<Date, Date> lastMonthRange = lastMonthRange("2017-07-28");
        logger.info("lastMonthRange : first day = {},last day = {}", lastMonthRange.getLeft(), lastMonthRange.getRight());
    }
}