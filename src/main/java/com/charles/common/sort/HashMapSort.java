package com.charles.common.sort;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HashMapSort {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(3, "ccccc");
        map.put(1, "aaaaa");
        map.put(2, "bbbbb");
        map.put(4, "ddddd");

        // 倒序和升序
        List<Map.Entry<Integer, String>> entries = map.entrySet().stream()
                // .sorted((o1, o2) -> o2.getKey().compareTo(o1.getKey())).collect(Collectors.toList());
                .sorted(Comparator.comparing(Map.Entry::getKey)).collect(Collectors.toList());

        for (Map.Entry<Integer, String> entry : entries) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
