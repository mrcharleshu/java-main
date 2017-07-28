package com.charles.algorithm;

import java.util.Arrays;

import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * 在要排序的一组数中，假设前面(n-1)[n>=2] 个数已经是排好顺序的，现在要把第n个数插到前面的有序数中，
 * 使得这n个数也是排好顺序的。如此反复循环，直到全部排好顺序。
 * for(;k<5;) 等价于 while(k<5)
 */
public class InsertSort {

    public static void main(String[] args) {
        int a[] = {49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35, 25, 53, 51};
        impl1(a);
        hyphenSeparator();
        impl2(a);
    }

    private static void impl1(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int temp = a[i];// 当前循环值
            int j;
            // 当前元素和前一个比较，一直找到比当前元素小的元素，停止比较
            for (j = i - 1; j >= 0 && temp < a[j]; j--) {
                System.out.println(String.format("i = %s, temp = %s, j = %s", i, temp, j));
                a[j + 1] = a[j];// 将大于temp的值整体后移一个单位
            }
            a[j + 1] = temp;
        }
        Arrays.stream(a).forEach(System.out::println);
    }

    private static void impl2(int[] a) {
        if (a == null || a.length < 2) {
            return;
        }
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                if (a[j] < a[j - 1]) {
                    int temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                    System.out.println(String.format("i = %s, temp = %s, j = %s", i, temp, j));
                } else {
                    break;
                }
            }
        }
        Arrays.stream(a).forEach(System.out::println);
    }
}