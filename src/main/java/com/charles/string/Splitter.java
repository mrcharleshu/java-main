package com.charles.string;

import java.util.Arrays;

/**
 * 正则需要转义字符：'$', '(', ')', '*', '+', '.', '[', ']', '?', '\\', '^', '{', '}', '|'
 * 异常现象： java.util.regex.PatternSyntaxException: Dangling meta. character '*' near index 0
 * 解决方法： 对特殊字符加\\转义即可。
 */
public class Splitter {

    public static void test1() {
        String str = "张三:李四,王五;赵六:孙七";
        String[] authors = str.split("[,;:]");
        Arrays.stream(authors).forEach(System.out::println);
    }

    public static void test2() {
        String str = "动漫电影*$$*新疆";
        String[] arr = str.split("\\*\\$\\$\\*");
        Arrays.stream(arr).forEach(System.out::println);
    }

    public static void main(String[] args) {
        test1();
        test2();
    }
}
