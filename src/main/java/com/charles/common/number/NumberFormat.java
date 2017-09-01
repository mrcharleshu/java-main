package com.charles.common.number;

import java.text.DecimalFormat;

public class NumberFormat {

    public static void main(String[] args) {
        DecimalFormat format = new DecimalFormat(".##");
        System.out.println(format.format(12.3456789));
    }
}
