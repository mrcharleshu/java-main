package com.charles.common.sort;

import java.util.Set;
import java.util.TreeSet;

public class ComparableSort {

    public static void main(String[] args) {
        Set<ComparableStudent> set = new TreeSet<>();     // Java 7的钻石语法(构造器后面的尖括号中不需要写类型)
        set.add(new ComparableStudent("Hao LUO", 33));
        set.add(new ComparableStudent("XJ WANG", 32));
        set.add(new ComparableStudent("Bruce LEE", 60));
        set.add(new ComparableStudent("Bob YANG", 22));
        set.forEach(System.out::println);
    }
}

class ComparableStudent implements Comparable<ComparableStudent> {
    private String name;
    private int age;

    public ComparableStudent(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "ComparableStudent [name=" + name + ", age=" + age + "]";
    }

    @Override
    public int compareTo(ComparableStudent o) {
        return this.age - o.age; // 比较年龄(年龄的升序)
    }

}