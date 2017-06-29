package com.charles.jdk8;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamSetTest {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("Charles", "Charles", "Job", "Lucy", "John", "Ben", "John");
        Set<String> set = list.stream().collect(Collectors.toSet());
        set.stream().forEach(System.out::println);
    }
}
