package com.charles.common.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * ArrayList是采用数组的形式保存对象的，这种方式将对象放在连续的内存块中，所以插入和删除时比较麻烦，查询比较方便。
 * LinkedList是将对象放在独立的空间中，而且每个空间中还保存下一个空间的索引，也就是数据结构中的链表结构，插入和删除比较方便，但是查找很麻烦，每次都要从第一个开始遍历
 * LinkedList的元素过多时普通的for花时间特别长，但是foreach花费的时间和ArrayList几乎一样
 */
public class LinkedListForeachLoop {
    //实例化arrayList
    private static final List<Integer> arrayList = new ArrayList<>();
    //实例化linkList
    private static final List<Integer> linkedList = new LinkedList<>();

    static {
        //插入10万条数据
        for (int i = 0; i < 100000; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
    }

    private static void printLog(final String msg, final long startMillis) {
        System.out.println(String.format(msg, System.currentTimeMillis() - startMillis));
    }

    private static void useForeachToLoopLinkedList() {
        //用foreach循环linkList
        long startMillis = System.currentTimeMillis();
        int link;
        for (Integer in : linkedList) {
            link = in;
        }
        printLog("用foreach循环 LinkedList 10万次花费时间：%s毫秒", startMillis);
    }

    private static void useForToLoopLinkedList() {
        //用for循环linkList
        long startMillis = System.currentTimeMillis();
        int link = 0;
        for (int i = 0; i < linkedList.size(); i++) {
            link = linkedList.get(i);
        }
        printLog("用for循环 LinkedList 10万次花费时间：%s毫秒", startMillis);
    }

    private static void useForeachToLoopArrayList() {
        //用foreach循环arrayList
        long startMillis = System.currentTimeMillis();
        int array;
        for (Integer in : arrayList) {
            array = in;
        }
        printLog("用foreach循环 ArrayList 10万次花费时间：%s毫秒", startMillis);
    }

    private static void useForToLoopArrayList() {
        //用for循环arrayList
        long startMillis = System.currentTimeMillis();
        int array = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            array = arrayList.get(i);
        }
        printLog("用for循环 ArrayList 10万次花费时间：%s毫秒", startMillis);
    }

    public static void main(String[] args) {
        hyphenSeparator("比较两个List在for和foreach中的不同");
        useForeachToLoopArrayList();
        hyphenSeparator();
        useForeachToLoopLinkedList();
        hyphenSeparator();
        useForToLoopArrayList();
        hyphenSeparator();
        useForToLoopLinkedList();
        hyphenSeparator("比较每个List在for和foreach中的不同");
        useForeachToLoopArrayList();
        hyphenSeparator();
        useForToLoopArrayList();
        hyphenSeparator();
        useForeachToLoopLinkedList();
        hyphenSeparator();
        useForToLoopLinkedList();
    }
}
