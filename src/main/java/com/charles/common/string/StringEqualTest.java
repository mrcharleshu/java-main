package com.charles.common.string;

/**
 * 什么情况下用+运算符进行字符串连接比调用StringBuffer/StringBuilder对象的append方法连接字符串性能更好？
 * <p>
 * String对象的intern方法会得到字符串对象在常量池中对应的版本的引用
 * （如果常量池中有一个字符串与String对象的equals结果是true），
 * 如果常量池中没有对应的字符串，则该字符串将被添加到常量池中，然后返回常量池中字符串的引用。
 */
class StringEqualTest {

    public static void main(String[] args) {
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program" + "ming";
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1 == s1.intern());
    }
}