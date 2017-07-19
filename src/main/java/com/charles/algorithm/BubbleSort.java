package com.charles.algorithm;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 冒泡排序算法
 */
public class BubbleSort<E extends Comparable<E>> extends Sort<E> {

    /**
     * 排序算法的实现，对数组中指定的元素进行排序
     * @param array 待排序的数组
     * @param c     比较器
     */
    public void sort(E[] array, Comparator<E> c) {
        // 需array.length - 1轮比较
        for (int i = 1; i < array.length; i++) {
            // 每轮循环中从最后一个元素开始向前起泡，直到i=j止，即i等于轮次止
            for (int j = array.length - 1; j >= i; j--) {
                //按照一种规则（后面元素不能小于前面元素）排序
                if (c.compare(array[j], array[j - 1]) < 0) {
                    //如果后面元素小于了（当然是大于还是小于要看比较器实现了）前面的元素，则前后交换
                    swap(array, j, j - 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {7, 2, 4, 3, 12, 1, 9, 6, 8, 5, 11, 10};
        BubbleSort<Integer> instance = new BubbleSort<>();
        instance.sort(arr, Integer::compareTo);
        // instance.sort(arr, (o1, o2) -> o2 - o1);
        // instance.sort(arr, (o1, o2) -> o1 - o2);
        // instance.sort(arr, Comparator.comparingInt(o -> o));
        Arrays.stream(arr).forEach(System.out::println);
    }
}
