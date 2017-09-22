package com.charles.common.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorSort {

    public static void main(String[] args) {
        List<ComparatorStudent> list = new ArrayList<>();// Java 7的钻石语法(构造器后面的尖括号中不需要写类型)
        list.add(new ComparatorStudent("Hao LUO", 33));
        list.add(new ComparatorStudent("XJ WANG", 32));
        list.add(new ComparatorStudent("Bruce LEE", 60));
        list.add(new ComparatorStudent("Bob YANG", 22));

        // 通过sort方法的第二个参数传入一个Comparator接口对象
        // 相当于是传入一个比较对象大小的算法到sort方法中
        // 由于Java中没有函数指针、仿函数、委托这样的概念
        // 因此要将一个算法传入一个方法中唯一的选择就是通过接口回调
        // Collections.sort(list, Comparator.comparing(ComparatorStudent::getName));
        // list.sort(Comparator.comparing(ComparatorStudent::getAge));
        Collections.sort(list, new Comparator<ComparatorStudent>() {
            @Override
            public int compare(ComparatorStudent o1, ComparatorStudent o2) {
                return o1.getAge().compareTo(o2.getAge());
            }
        });
        list.forEach(System.out::println);
    }

    private static class ComparatorStudent {
        private String name;
        private Integer age;

        public ComparatorStudent(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Student [name=" + name + ", age=" + age + "]";
        }
    }
}
