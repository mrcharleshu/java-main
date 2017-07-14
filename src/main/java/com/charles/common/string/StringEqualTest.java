package com.charles.common.string;

import static com.charles.utils.LineSeperators.hyphenSeparator;

/**
 * 什么情况下用+运算符进行字符串连接比调用StringBuffer/StringBuilder对象的append方法连接字符串性能更好？
 * <p>
 * String对象的intern方法会得到字符串对象在常量池中对应的版本的引用
 * （如果常量池中有一个字符串与String对象的equals结果是true），
 * 如果常量池中没有对应的字符串，则该字符串将被添加到常量池中，然后返回常量池中字符串的引用。
 */
class StringEqualTest {

    public static void main(String[] args) {
        test1();
        hyphenSeparator();
        test2();
    }

    public static void test1() {
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program" + "ming";
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1 == s1.intern());
    }

    public static void test2() {
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = "Hel" + "lo";
        String s4 = "Hel" + new String("lo");
        String s5 = new String("Hello");
        String s6 = s5.intern();
        String s7 = "H";
        String s8 = "ello";
        String s9 = s7 + s8;

        // s1 == s2这个非常好理解，s1、s2在赋值时，均使用的字符串字面量，说白话点，就是直接把字符串写死，在编译期间，
        // 这种字面量会直接放入class文件的常量池中，从而实现复用，载入运行时常量池后，s1、s2指向的是同一个内存地址，所以相等。
        System.out.println(s1 == s2);  // true
        // s1 == s3这个地方有个坑，s3虽然是动态拼接出来的字符串，但是所有参与拼接的部分都是已知的字面量，在编译期间，
        // 这种拼接会被优化，编译器直接帮你拼好，因此String s3 = "Hel" + "lo";在class文件中被优化成String s3 = "Hello";，所以s1 == s3成立。
        System.out.println(s1 == s3);  // true
        // s1 == s4当然不相等，s4虽然也是拼接出来的，但new String("lo")这部分不是已知字面量，是一个不可预料的部分，
        // 编译器不会优化，必须等到运行时才可以确定结果，结合字符串不变定理，鬼知道s4被分配到哪去了，所以地址肯定不同
        System.out.println(s1 == s4);  // false
        // 虽然s7、s8在赋值的时候使用的字符串字面量，但是拼接成s9的时候，s7、s8作为两个变量，都是不可预料的，编译器毕竟是编译器，
        // 不可能当解释器用，所以不做优化，等到运行时，s7、s8拼接成的新字符串，在堆中地址不确定，不可能与方法区常量池中的s1地址相同
        System.out.println(s1 == s9);  // false
        // s4 == s5已经不用解释了，绝对不相等，二者都在堆中，但地址不同。
        System.out.println(s4 == s5);  // false
        // s1 == s6这两个相等完全归功于intern方法，s5在堆中，内容为Hello ，intern方法会尝试将Hello字符串添加到常量池中，
        // 并返回其在常量池中的地址，因为常量池中已经有了Hello字符串，所以intern方法直接返回地址；
        // 而s1在编译期就已经指向常量池了，因此s1和s6指向同一地址，相等。
        System.out.println(s1 == s6);  // true
    }
}