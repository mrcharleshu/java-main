package com.charles.common.collection;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

public class ListRemoveInForeachLoop implements Iterable<String> {
    private static final int LIST_SIZE = 10;
    private final List<String> list = Lists.newArrayList();

    private ListRemoveInForeachLoop() {
        for (int i = 0; i < LIST_SIZE; i++) {
            list.add("Charles" + i);
        }
    }

    @Override
    public Iterator<String> iterator() {
        return list.iterator();
    }

    public List<String> getList() {
        return list;
    }

    private static void forRemove() {
        List<String> list = new ListRemoveInForeachLoop().getList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("Charles3")) {
                list.remove(i);
            }
        }
        list.forEach(System.out::println);
        System.out.println("list size = " + list.size());
    }

    private static void foreachRemove() {
        ListRemoveInForeachLoop listForeachPrint = new ListRemoveInForeachLoop();
        for (String str : listForeachPrint) {
            if (str.equals("Charles3")) {
                // Exception in thread "main" java.util.ConcurrentModificationException
                listForeachPrint.getList().remove(str);
            }
        }
    }

    public static void main(String[] args) {
        forRemove();
        foreachRemove();
    }
}
