package com.charles.utils;

import com.google.common.collect.Lists;

import java.util.List;

public class PrintListByStep {

    public static void main(String[] args) {
        final int STEP_SIZE = 8;
        List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        printByGap(STEP_SIZE, list);
    }

    private static void printByGap(final int STEP_SIZE, final List<Integer> list) {
        int currentIndex = 0, remainSize = list.size();
        while (remainSize >= STEP_SIZE) {
            print(list, currentIndex, currentIndex + STEP_SIZE);
            currentIndex += STEP_SIZE;
            remainSize -= STEP_SIZE;
        }
        print(list, list.size() - remainSize, list.size());
    }

    private static void print(final List<Integer> list, final int fromIndex, final int toIndex) {
        System.out.println(list.subList(fromIndex, toIndex));
    }
}
