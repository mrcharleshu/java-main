package com.charles.common.sort;

import com.google.common.collect.Lists;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static com.charles.utils.LineSeparators.hyphenSeparator;

public class StreamSort {
    public static void main(String[] args) {
        LocalDateTime dt1 = LocalDateTime.of(2017, 4, 10, 11, 12, 13);
        LocalDateTime dt2 = LocalDateTime.of(2017, 9, 22, 6, 23, 45);
        LocalDateTime dt3 = LocalDateTime.of(2016, 11, 20, 12, 10, 10);
        LocalDateTime dt4 = LocalDateTime.of(2015, 3, 15, 9, 34, 57);
        List<LocalDateTime> list = Lists.newArrayList(dt1, dt2, dt3, dt4);
        list.stream().sorted().forEach(System.out::println);
        hyphenSeparator();
        list.stream().sorted(Comparator.naturalOrder()).forEach(System.out::println);
        hyphenSeparator();
        list.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
    }
}
