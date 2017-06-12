package com.charles.common_object.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class ListIntersection {

    public static void main(String[] args) {
        List<String> list1 = Lists.newArrayList("1111", "2222", "3333");
        List<String> list2 = Lists.newArrayList("4444", "5555", "3333");

        Set<String> set1 = Sets.newHashSet("1111", "2222", "3333");
        Set<String> set2 = Sets.newHashSet("4444", "5555", "3333");
        set1.retainAll(set2);

        //并集
        //list1.addAll(list2);
        //交集
        list1.retainAll(list2);
        //差集
        //list1.removeAll(list2);
        //无重复并集
//        list2.removeAll(list1);
//        list1.addAll(list2);
        // list1.forEach(System.out::println);
        set1.forEach(System.out::println);
    }

}
