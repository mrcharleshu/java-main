package com.charles.common.date;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * ISO 8601 duration
 */
public class Duration_ISO_8601 {
    private enum TimeUnits {
        年, 个月, 天, 小时, 分钟, 秒
    }

    public static void main(String[] args) {
        // 提醒周期
        final String period = "PT5H";
        // 已过时间（单位秒）
        final long passedSeconds = 3600 * 4;
        Duration leftDuration = Duration.parse(period).minusSeconds(passedSeconds);
        System.out.println(leftDuration.toString());
        // 快结束前多久提醒
        Duration notifyBefore = Duration.parse("PT2H");
        System.out.println(leftDuration.compareTo(notifyBefore));
        hyphenSeparator();
        LocalDateTime fromTime = LocalDateTime.parse("2017-12-04T01:23:34");
        LocalDateTime toTime = LocalDateTime.parse("2018-03-07T10:12:27");
        System.out.println(Duration.between(fromTime, toTime));
        System.out.println(getDuration(fromTime, toTime));
    }

    private static String getDuration(final LocalDateTime fromTime, final LocalDateTime toTime) {
        LocalDateTime tempFromTime = LocalDateTime.of(LocalDate.now(), fromTime.toLocalTime());
        LocalDateTime tempToTime = LocalDateTime.of(LocalDate.now(), toTime.toLocalTime());
        if (fromTime.toLocalTime().isAfter(toTime.toLocalTime())) {
            StringBuilder dateStringBuilder = getDatePeriod(fromTime.toLocalDate().plusDays(1), toTime.toLocalDate());
            StringBuilder timeStringBuilder = getTimeDuration(tempFromTime.minusDays(1), tempToTime);
            return dateStringBuilder.append(timeStringBuilder).toString();
        } else {
            StringBuilder dateStringBuilder = getDatePeriod(fromTime.toLocalDate(), toTime.toLocalDate());
            StringBuilder timeStringBuilder = getTimeDuration(tempFromTime, tempToTime);
            return dateStringBuilder.append(timeStringBuilder).toString();
        }
    }

    private static StringBuilder getTimeDuration(final LocalDateTime fromTime, final LocalDateTime toTime) {
        StringBuilder sb = new StringBuilder();
        Duration duration = Duration.between(fromTime, toTime);
        if (duration.toHours() > 0) {
            sb.append(duration.toHours()).append(TimeUnits.小时);
        }
        duration = duration.minusHours(duration.toHours());
        if (duration.toMinutes() > 0) {
            sb.append(duration.toMinutes()).append(TimeUnits.分钟);
        }
        duration = duration.minusMinutes(duration.toMinutes());
        if (duration.getSeconds() > 0) {
            sb.append(duration.getSeconds()).append(TimeUnits.秒);
        }
        return sb;
    }

    private static StringBuilder getDatePeriod(final LocalDate fromDate, final LocalDate toDate) {
        StringBuilder sb = new StringBuilder();
        Period period = Period.between(fromDate, toDate);
        if (period.getYears() > 0) {
            sb.append(period.getYears()).append(TimeUnits.年);
        }
        if (period.getMonths() > 0) {
            sb.append(period.getMonths()).append(TimeUnits.个月);
        }
        if (period.getDays() > 0) {
            sb.append(period.getDays()).append(TimeUnits.天);
        }
        return sb;
    }
}
