package com.charles.common.collection;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class CollectionsSort {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(2, 3, 6, 3, 1, 9, 4);
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
        Collections.sort(list, (a, b) -> b - a);
        System.out.println(list);
    }
}