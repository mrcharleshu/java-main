package com.charles.common.date;

import java.time.Duration;

/**
 * ISO 8601 duration
 */
public class Duration_ISO_8601 {
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
    }
}
