package com.charles.common;

public class MathMethod {

    public static void main(String[] args) {
        int ceil = (int) Math.ceil(5 / 2);
        System.out.println(ceil);
        int floor = (int) Math.floor(5 / 2);
        System.out.println(floor);
        // Math.round(11.5)的返回值是12，Math.round(-11.5)的返回值是-11。
        // 四舍五入的原理是在参数上加0.5然后进行下取整
        long round1 = Math.round(11.5);
        System.out.println(round1);
        long round2 = Math.round(-11.5);
        System.out.println(round2);
    }
}