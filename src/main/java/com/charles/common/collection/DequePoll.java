package com.charles.common.collection;

import java.util.ArrayDeque;
import java.util.Deque;

public class DequePoll {

    public static void main(String[] args) {
        Deque<String> subJobs = new ArrayDeque<>();
        subJobs.add("Charles");
        subJobs.add("John");
        subJobs.add("Mary");
        System.out.println(subJobs.pollFirst() + " " + subJobs.size());
        System.out.println(subJobs.pollFirst() + " " + subJobs.size());
    }
}
