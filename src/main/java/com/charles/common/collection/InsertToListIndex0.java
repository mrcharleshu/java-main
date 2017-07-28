package com.charles.common.collection;

import com.google.common.collect.Lists;

import java.util.List;

import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * 把对象插入列表的下标0中
 */
public class InsertToListIndex0 {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayListWithCapacity(3);
        list.add("Charles");
        list.add("John");
        list.forEach(System.out::println);
        hyphenSeparator();
        list.add(0, "Sonya");
        list.forEach(System.out::println);
        list.add("Mary");
    }
}
