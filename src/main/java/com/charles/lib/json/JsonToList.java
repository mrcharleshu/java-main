package com.charles.lib.json;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class JsonToList {

    public static void main(String[] args) {
        String json = "[\"Charles\",\"Lucy\",\"John\"]";
        // JSONObject obj = JSONObject.parse(json);
        // JSONArray data =         JSONArray.parse(json);
        List<String> list = (List) JSON.parse(json);
        list.stream().forEach(System.out::println);
        // List<要转成的model> plist = data.toList(data, 要转成的model.class);
    }
}
