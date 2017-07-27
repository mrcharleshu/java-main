package com.charles.common.fp;

import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.charles.utils.LineSeperators.hyphenSeparator;

/**
 * 函数式编程特性
 */
public class FPCharacteristics {
    // lambda递归实现阶乘
    private static UnaryOperator<Integer> factorial1 = x -> ((x == 1 || x == 0)
            ? 1 : x * FPCharacteristics.factorial1.apply(x - 1));

    private static UnaryOperator<Integer> factorial2 = new UnaryOperator<Integer>() {
        @Override
        public Integer apply(Integer x) {
            return ((x == 1 || x == 0)
                    ? 1 : x * FPCharacteristics.factorial2.apply(x - 1));
        }
    };

    public static void main(String[] args) {
        // 一、闭包和高阶函数
        // 为了方便使用函数式风格，jdk8让每个集合都可以转换成一个stream，然后就可以使用那些著名的map、reduce、filter等函数了”
        Stream<String> stream1 = Arrays.asList("Hello", "Java8", "Java7").stream().map(s -> s.toUpperCase());
        System.out.println(stream1.collect(Collectors.toList()));
        hyphenSeparator();
        // map 是个高阶函数，它接受了一个Labmda表达式（匿名函数）作为参数，
        // 把Stream中的元素做了变换：字符串变成了大写 ，然后返回了一个新的Stream

        // 二、惰性计算
        // 这也是延迟计算，即使你加了一个打印语句，也不会有任何任何输出：
        Stream<String> stream2 = Arrays.asList("Hello", "Java8", "Java7").stream().map(s -> {
            System.out.println(s);
            return s.toLowerCase();
        });
        System.out.println("println inside map did not execute...");
        System.out.println(stream2.collect(Collectors.toList()));
        hyphenSeparator();
        List<String> stream3 = Stream.of("Hello", "Java8", "Java7").filter(s -> "Java8".equals(s)).collect(Collectors.toList());
        System.out.println(stream3);
        hyphenSeparator();
        int stream4 = Stream.of(1, 2, 3, 4, 5).reduce(0, (p, c) -> p + c);
        System.out.println(stream4);
        hyphenSeparator();
        // 三、递归实现
        System.out.println(FPCharacteristics.factorial1.apply(5));
        System.out.println(FPCharacteristics.factorial2.apply(5));
    }
}
