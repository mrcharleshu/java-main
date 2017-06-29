package com.charles.lib.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public class JsonToMapList {

    public static void main(String[] args) {
        String strArr = "[{\"name\":\"Charles\",\"age\":\"25\",\"gender\":\"M\",\"phone\":\"17701618907\"}," +
                "{\"name\":\"Lucy\",\"age\":\"15\",\"gender\":\"F\",\"phone\":\"17321219276\"}]";
        //第一种方式
        List<Map<String, Object>> listObjectFir = (List<Map<String, Object>>) JSONArray.parse(strArr);
        System.out.println("利用JSONArray中的parse方法来解析json数组字符串");
        for (Map<String, Object> mapList : listObjectFir) {
            for (Map.Entry entry : mapList.entrySet()) {
                System.out.println(entry.getKey() + "  " + entry.getValue());
            }
        }
        //第二种方式
        List<Map<String, String>> listObjectSec = JSONArray.parseObject(strArr, List.class);
        System.out.println("利用JSONArray中的parseObject方法并指定返回类型来解析json数组字符串");
        for (Map<String, String> mapList : listObjectSec) {
            for (Map.Entry entry : mapList.entrySet()) {
                System.out.println(entry.getKey() + "  " + entry.getValue());
            }
        }
        //第三种方式
        JSONArray listObjectThir = JSONArray.parseArray(strArr);
        System.out.println("利用JSONArray中的parseArray方法来解析json数组字符串");
        for (Object mapList : listObjectThir) {
            for (Object entry : ((Map) mapList).entrySet()) {
                System.out.println(((Map.Entry) entry).getKey() + "  " + ((Map.Entry) entry).getValue());
            }
        }
        //第四种方式
        List listObjectFour = JSONArray.parseArray(strArr, Map.class);
        System.out.println("利用JSONArray中的parseArray方法并指定返回类型来解析json数组字符串");
        for (Object mapList : listObjectFour) {
            for (Object entry : ((Map) mapList).entrySet()) {
                System.out.println(((Map.Entry) entry).getKey() + "  " + ((Map.Entry) entry).getValue());
            }
        }
        //第五种方式
        JSONArray listObjectFifth = JSONObject.parseArray(strArr);
        System.out.println("利用JSONObject中的parseArray方法来解析json数组字符串");
        for (Object mapList : listObjectFifth) {
            for (Object entry : ((Map) mapList).entrySet()) {
                System.out.println(((Map.Entry) entry).getKey() + "  " + ((Map.Entry) entry).getValue());
            }
        }
        //第六种方式
        List listObjectSix = JSONObject.parseArray(strArr, Map.class);
        System.out.println("利用JSONObject中的parseArray方法并指定返回类型来解析json数组字符串");
        for (Object mapList : listObjectSix) {
            for (Object entry : ((Map) mapList).entrySet()) {
                System.out.println(((Map.Entry) entry).getKey() + "  " + ((Map.Entry) entry).getValue());
            }
        }
        //第七种方式
        JSONArray listObjectSeven = JSON.parseArray(strArr);
        System.out.println("利用JSON中的parseArray方法来解析json数组字符串");
        for (Object mapList : listObjectSeven) {
            for (Object entry : ((Map) mapList).entrySet()) {
                System.out.println(((Map.Entry) entry).getKey() + "  " + ((Map.Entry) entry).getValue());
            }
        }
        //第八种方式
        List listObjectEigh = JSONObject.parseArray(strArr, Map.class);
        System.out.println("利用JSON中的parseArray方法并指定返回类型来解析json数组字符串");
        for (Object mapList : listObjectEigh) {
            for (Object entry : ((Map) mapList).entrySet()) {
                System.out.println(((Map.Entry) entry).getKey() + "  " + ((Map.Entry) entry).getValue());
            }
        }
    }
}
