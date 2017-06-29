package com.charles.common.collection;

import com.google.common.collect.Lists;

import java.util.List;

public class InsertIndex0 {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayListWithCapacity(3);
        list.add("Charles");
        list.add("John");
        list.forEach(System.out::println);
        list.add(0, "Sonya");
        System.out.println();
        list.forEach(System.out::println);
        list.add("Mary");
    }
}
