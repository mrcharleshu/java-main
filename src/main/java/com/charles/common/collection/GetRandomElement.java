package com.charles.common.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
public class GetRandomElement {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Charles");
        list.add("John");
        list.add("Colin");
        list.add("Mary");
        IntStream.range(0, 10).forEach(o -> {
            System.out.println(new Random().nextInt(500) + 100);
            double rand = new Random().nextDouble();
            int index = (int) (rand * list.size());
            log.info("rand:{}, toInt:{}, value:{}, nextInt:{}",
                    rand, index, list.get(index), new Random().nextInt(list.size()));
        });
    }
}
