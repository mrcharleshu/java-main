package com.charles.common.string;

import org.apache.commons.lang3.StringUtils;

/**
 * 可以自己写实现也可以使用String或StringBuffer/StringBuilder中的方法。有一道很常见的面试题是用递归实现字符串反转
 */
public class StringReverse {

    public static void main(String[] args) {
        String str1 = "Hello World";
        System.out.println(reverse(str1));
        System.out.println(StringUtils.reverse("Charles"));
        System.out.println(new StringBuilder("Java").reverse());
        System.out.println(new StringBuffer("String").reverse());
    }

    public static String reverse(String originStr) {
        if (originStr == null || originStr.length() <= 1)
            return originStr;
        String output = reverse(originStr.substring(1)) + originStr.charAt(0);
        System.out.println(String.format("length=%s %s -> %s", originStr.length(), originStr, output));
        return output;
    }
}
