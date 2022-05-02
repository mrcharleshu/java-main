package com.charles.jdk8;

import org.apache.lucene.util.RamUsageEstimator;

public class SizeTool {

    public static void main(String[] args) {
        // 4(object header)+4(pointer)+4(length)+4*10(10个int大小)=52byte 由于需要8位对齐，所以最终大小为`56byte`。
        System.out.println("new int[10]: " + RamUsageEstimator.sizeOf(new int[10]));
        // 8 + 4 + 4 (对象填充4)
        System.out.println("new Integer(4)): " + RamUsageEstimator.sizeOf(new Integer(4)));
        //
        System.out.println("new String():" + RamUsageEstimator.sizeOf(new String()));
        System.out.println("new String(\"byte\"):" + RamUsageEstimator.sizeOf(new String("byte")));
        System.out.println("new String(\"Charles\")" + RamUsageEstimator.sizeOf(new String("Charles")));
        // 8 + 4 + （ref 4*2）整形16，String 40 + 2 *7 = 54
        // 8 + 8 + 16 + 56
        System.out.println(RamUsageEstimator.sizeOf(new Person(30, "Charles")));
        System.out.println(RamUsageEstimator.sizeOf(new Person(30, null)));
    }

    public static class Person {
        private final Integer age;
        private final String name;

        public Person(Integer age, String name) {
            this.age = age;
            this.name = name;
        }
    }
}