package com.charles.common.collection;

import com.google.common.collect.Lists;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import static com.charles.utils.LineSeparators.hyphenSeparator;

public class ListInsertAndRemove implements Iterable<String> {
    private static final int LIST_SIZE = 10;
    private static final List<String> values = Lists.newArrayList();

    private ListInsertAndRemove() {
        for (int i = 0; i < LIST_SIZE; i++) {
            values.add("Charles" + i);
        }
    }

    @Override
    public Iterator<String> iterator() {
        return values.iterator();
    }

    public List<String> getValues() {
        return values;
    }

    private static void forRemove() {
        List<String> list = new ListInsertAndRemove().getValues();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("Charles3")) {
                list.remove(i);
            }
        }
        list.forEach(System.out::println);
        System.out.println("values size = " + list.size());
    }

    private static void foreachRemove() throws ConcurrentModificationException {
        ListInsertAndRemove listForeachPrint = new ListInsertAndRemove();
        for (String str : listForeachPrint) {
            if (str.equals("Charles3")) {
                // Exception in thread "main" java.util.ConcurrentModificationException
                listForeachPrint.getValues().remove(str);
            }
        }
    }

    private static void simpleInsert() {
        List<String> list = Lists.newArrayListWithCapacity(3);
        list.add("Charles");
        list.add("John");
        list.forEach(System.out::println);
        hyphenSeparator();
        list.add(0, "Sonya");
        list.forEach(System.out::println);
        hyphenSeparator();
        list.add("Mary");
        list.add(0, "Colin");
        list.forEach(System.out::println);
    }

    private static void forAdd() {
        List<String> list = new ListInsertAndRemove().getValues();
        for (int i = 0; i < list.size(); i++) {
            // Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
            // if (list.get(i).equals("Charles3")) { //会有死循环到
            if (i == 3) {
                list.add(i, "Colin");
            }
        }
        list.forEach(System.out::println);
        System.out.println("values size = " + list.size());
    }

    private static void foreachAdd() throws ConcurrentModificationException {
        ListInsertAndRemove listForeachPrint = new ListInsertAndRemove();
        for (String str : listForeachPrint) {
            if (str.equals("Charles3")) {
                // Exception in thread "main" java.util.ConcurrentModificationException
                listForeachPrint.getValues().add("Colin");
            }
        }
    }

    public static void main(String[] args) {
        hyphenSeparator("simpleInsert");
        simpleInsert();
        hyphenSeparator("forAdd");
        forAdd();
        hyphenSeparator("foreachAdd");
        try {
            foreachAdd();
        } catch (Exception e) {
            e.printStackTrace();
        }
        hyphenSeparator("forRemove");
        forRemove();
        hyphenSeparator("foreachRemove");
        try {
            foreachRemove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
