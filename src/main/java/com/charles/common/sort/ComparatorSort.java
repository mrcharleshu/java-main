package com.charles.common.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.charles.utils.LineSeparators.hyphenSeparator;

public class ComparatorSort {
    
    public static void main(String[] args) {
        List<ComparatorStudent> list = new ArrayList<>();// Java 7的钻石语法(构造器后面的尖括号中不需要写类型)
        list.add(new ComparatorStudent("Hao LUO", 33, 175));
        list.add(new ComparatorStudent("XJ WANG", 32, 168));
        list.add(new ComparatorStudent("Bob YANG", 22, 183));
        list.add(new ComparatorStudent("Bruce LEE", 33, 174));
        list.add(new ComparatorStudent("Abraham", 33, 174));
        list.add(new ComparatorStudent("Charles", 32, 177));
        list.add(new ComparatorStudent("Sonya", 25, 162));

        // 通过sort方法的第二个参数传入一个Comparator接口对象
        // 相当于是传入一个比较对象大小的算法到sort方法中
        // 由于Java中没有函数指针、仿函数、委托这样的概念
        // 因此要将一个算法传入一个方法中唯一的选择就是通过接口回调
        // Collections.sort(list, Comparator.comparing(ComparatorStudent::getName));
        // list.sort(Comparator.comparing(ComparatorStudent::getAge));
        // list.forEach(System.out::println);
        hyphenSeparator();
        // complexSort(list);
        list.sort(Comparator.comparing(ComparatorStudent::getAge)
                .thenComparing(ComparatorStudent::getHeight)
                .thenComparing(ComparatorStudent::getName));
        list.forEach(System.out::println);
    }

    private static void complexSort(final List<ComparatorStudent> list) {
        list.sort((o1, o2) -> {
            if (o1.getAge() > o2.getAge()) {
                return 1;
            } else if (o1.getAge().equals(o2.getAge())) {
                return o1.getHeight().compareTo(o2.getHeight());
            } else {
                return -1;
            }
        });
    }

    private static class ComparatorStudent {
        private final String name;
        private final Integer age;
        private final Integer height;

        public ComparatorStudent(final String name, final Integer age, final Integer height) {
            this.name = name;
            this.age = age;
            this.height = height;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public Integer getHeight() {
            return height;
        }

        @Override
        public String toString() {
            return "ComparatorStudent{" + "name='" + name + '\'' + ", age=" + age + ", height=" + height + '}';
        }
    }
}
