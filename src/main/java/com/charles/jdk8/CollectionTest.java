package com.charles.jdk8;

import com.google.common.collect.Lists;

import java.util.List;

public class CollectionTest {
    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("John", "Mary", "Lucy");
        list.add(0, "Charles");
        System.out.println(list);
    }
}
