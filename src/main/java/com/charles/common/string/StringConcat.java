package com.charles.common.string;

import static com.charles.utils.LineSeperators.hyphenSeparator;

/**
 * 字符串拼接不要直接用 String 相加，StringBuilder 的效率要比 String 直接相加拼接要高。
 * StringBuffer 是同步的（线程安全的）， StringBuilder 不是线程安全的，同步带来了性能消耗。
 * 那么 String 、 StringBuilder 、 StringBuffer 这三者的效率到底有多大的差距呢？
 * Talk is cheap, Let's code
 */
public class StringConcat {

    public static void main(String[] args) {
        printResult(100, true);
        hyphenSeparator();
        printResult(1000, true);
        hyphenSeparator();
        printResult(10000, true);
        hyphenSeparator();
        printResult(100000, true);
        hyphenSeparator();
        hyphenSeparator();
        printResult(1000000, false);
        hyphenSeparator();
        printResult(10000000, false);

    }

    public static void printResult(long loopCount, boolean runStringConcat) {
        System.out.println("loopCount:" + loopCount);
        if (runStringConcat) {
            stringConcat(loopCount);
        }
        stringBufferAppend(loopCount);
        stringBuilderAppend(loopCount);
    }

    public static long stringConcat(long loopCount) {
        long beginTime = System.currentTimeMillis();
        String str = "hello,world!";
        String result = "";

        for (int i = 0; i < loopCount; i++) {
            result += str;
        }
        assert !result.isEmpty();
        long consumeTime = System.currentTimeMillis() - beginTime;
        System.out.println("String concat millis:" + consumeTime);
        return consumeTime;
    }

    public static long stringBuilderAppend(long loopCount) {
        long beginTime = System.currentTimeMillis();
        String str = "hello, world!";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < loopCount; i++) {
            stringBuilder.append(str);
        }
        String result = stringBuilder.toString();
        assert !result.isEmpty();
        long consumeTime = System.currentTimeMillis() - beginTime;
        System.out.println("StringBuilder append millis:" + consumeTime);
        return consumeTime;

    }

    public static long stringBufferAppend(long loopCount) {
        long beginTime = System.currentTimeMillis();
        String str = "hello, world!";
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < loopCount; i++) {
            stringBuffer.append(str);
        }
        String result = stringBuffer.toString();
        assert !result.isEmpty();
        long consumeTime = System.currentTimeMillis() - beginTime;
        System.out.println("StringBuffer append millis:" + consumeTime);
        return consumeTime;
    }
}
//loopCount:100
//String concat millis:0
//StringBuffer append millis:0
//StringBuilder append millis:0
//-------------------------------------------
//loopCount:1000
//String concat millis:33
//StringBuffer append millis:1
//StringBuilder append millis:0
//-------------------------------------------
//loopCount:10000
//String concat millis:1150
//StringBuffer append millis:0
//StringBuilder append millis:1
//-------------------------------------------
//loopCount:100000
//String concat millis:73858
//StringBuffer append millis:4
//StringBuilder append millis:5
//-------------------------------------------
//-------------------------------------------
//loopCount:1000000
//StringBuffer append millis:39
//StringBuilder append millis:33
//-------------------------------------------
//loopCount:10000000
//StringBuffer append millis:364
//StringBuilder append millis:498