package com.charles.common.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

import static com.charles.utils.LineSeperators.hyphenSeparator;

/**
 * 两个集合的交叉方法
 */
public class TwoCollCrossMethod {

    public static void main(String[] args) {
        List<String> list1 = Lists.newArrayList("1111", "2222", "3333");
        List<String> list2 = Lists.newArrayList("4444", "5555", "3333");
        // list1.retainAll(list2);//交集
        // list1.addAll(list2);//并集
        list1.removeAll(list2);//差集(去掉相同的部分)

        Set<String> set1 = Sets.newHashSet("1111", "2222", "3333");
        Set<String> set2 = Sets.newHashSet("4444", "5555", "3333");
        // set1.retainAll(set2);//交集
        // set1.addAll(set2);//并集
        set1.removeAll(set2);//差集(去掉相同的部分)

        list1.forEach(System.out::println);
        hyphenSeparator();
        set1.forEach(System.out::println);
    }
}
