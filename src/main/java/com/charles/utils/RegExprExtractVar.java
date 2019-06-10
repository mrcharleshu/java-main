package com.charles.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * （代码编写30分钟内完成）
 * 日志统计问题
 * 我们现在的日志格式是这样的：[thread-id][request-id][yyyy-MM-dd hh:mm:ss ms]log body，
 * 依次是线程id、request-id（每次请求的request-id是同一个）、时间戳、日志文本，请您写个函
 * 数（只用jdk自带的类），实现：
 * 1、找出哪一秒日志数量最多，打印时间和日志条数；
 * 2、统计平均每个请求产生多少条日志；
 * 3、附带一些测试数据和测试结果
 * 需要当生产正式代码写，我们会考察代码的可读性、严谨性、写代码的速度和质量等。
 * <p>
 * 注意！！！！为了节省时间，您不用读取文件，直接传输一个日志字符串给您，另外日志时间可能是断断续续的，不是每秒都有。
 * 示例日志字符串参考：
 * [thread-1][100000][2019-03-15 13:07:42.164]login
 * [thread-1][100000][2019-03-15 13:07:42.165]read db
 * [thread-1][100000][2019-03-15 13:07:42.166]return json to frontend
 * [thread-2][100001][2019-03-15 13:07:47.102]login
 * Pattern / Matcher
 * find()方法，用来搜索与正则表达式相匹配的任何目标字符串，
 * group()方法，用来返回包含了所匹配文本的字符串
 */
public class RegExprExtractVar {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegExprExtractVar.class);
    // [thread-id][request-id][yyyy-MM-dd hh:mm:ss ms]log body，
    // private static final Pattern LOG_PATTERN_1 = Pattern.compile("^(\\[.*\\])(\\[.*\\])(\\[.*\\])(.*)$");
    private static final Pattern LOG_PATTERN_1 = Pattern.compile("^\\[(.*)\\]\\[(.*)\\]\\[(.*)\\](.*)$");
    private static final Pattern LOG_PATTERN_2 = Pattern.compile("^(.*?)结算单号[:：][\\[\\【](.+)[\\]\\】]");

    private List<String> readLogToList() {
        List<String> logs = new ArrayList<>();
        logs.add("[thread-1][100000][2019-03-15 13:07:42.164]login");
        logs.add("[thread-1][100000][2019-03-15 13:07:42.165]read db");
        logs.add("[thread-1][100000][2019-03-15 13:07:42.166]return json to frontend");
        logs.add("[thread-2][100001][2019-03-15 13:07:47.102]login");
        return logs;
    }

    private List<String> readRemarksToList() {
        List<String> remarks = new ArrayList<>();
        remarks.add("sdfafsdaf结算单号:[AD2423431]");
        remarks.add("sdfafsdaf结算单号：[AD2423432]");
        remarks.add("sdfafsdaf结算单号:【AD2423433】");
        remarks.add("sdfafsdaf结算单号：【AD2423434】");
        remarks.add("结算单号:[AD2423435]");
        return remarks;
    }

    public static void main(String[] args) {
        pattern1();
        pattern2();
    }

    private static void pattern2() {
        Matcher matcher;
        List<String> remarks = new RegExprExtractVar().readRemarksToList();
        for (String remark : remarks) {
            matcher = LOG_PATTERN_2.matcher(remark);
            while (matcher.find()) {
                String preText = matcher.group(1);
                String settlementNo = matcher.group(2);
                LOGGER.info("preText = {}, settlementNo = {}", preText, settlementNo);
            }
        }
    }

    private static void pattern1() {
        Map<String, Integer> everySecondsLogCounterMap = new HashMap<>();
        Map<String, Integer> everyRequestLogCounterMap = new HashMap<>();
        Matcher matcher;
        List<String> logs = new RegExprExtractVar().readLogToList();
        for (String log : logs) {
            matcher = LOG_PATTERN_1.matcher(log);
            while (matcher.find()) {
                String requestId = matcher.group(2);
                String time = matcher.group(3);
                System.out.println(requestId);
                System.out.println(time);
                String timeWithoutMillis = time.split("\\.")[0];
                everySecondsLogCounterMap.putIfAbsent(timeWithoutMillis, 0);
                everySecondsLogCounterMap.put(timeWithoutMillis, everySecondsLogCounterMap.get(timeWithoutMillis) + 1);
                everyRequestLogCounterMap.putIfAbsent(requestId, 0);
                everyRequestLogCounterMap.put(requestId, everyRequestLogCounterMap.get(requestId) + 1);
            }
        }
        Map.Entry<String, Integer> mostLogSecond = everySecondsLogCounterMap.entrySet()
                .stream().min((o1, o2) -> o2.getValue().compareTo(o1.getValue())).get();
        System.out.println(String.format("time = %s, log count = %s", mostLogSecond.getKey(), mostLogSecond.getValue()));
    }
}
