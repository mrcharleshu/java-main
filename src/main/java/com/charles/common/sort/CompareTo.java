package com.charles.common.sort;

/**
 * compareTo() 方法用于将 Number 对象与方法的参数进行比较。可用于比较 Byte, Long, Integer等。
 * 该方法用于两个相同数据类型的比较，两个不同类型的数据不能用此方法来比较。
 * <pre>
 * public int compareTo( NumberSubClass referenceName )
 * 参数：referenceName -- 可以是一个 Byte, Double, Integer, Float, Long 或 Short 类型的参数。
 * </pre>
 * 如果指定的数与参数相等返回0。
 * 如果指定的数小于参数返回 -1。
 * 如果指定的数大于参数返回 1。
 */
public class CompareTo {

    public static void main(String[] args) {
        Integer x = 5;
        System.out.println(x.compareTo(3));
        System.out.println(x.compareTo(5));
        System.out.println(x.compareTo(8));
    }
}