package com.charles.common.map;

import com.google.common.collect.Maps;

import java.util.Map;

public class MapMethod {

    public static void main(String[] args) {
        Map<String, String> map = Maps.newHashMap();
        map.put("Charles", "Programmer");
        map.put("Lily", "Teacher");
        map.forEach((key, value) -> System.out.println(String.format("%s-%s", key, value)));
    }
}
