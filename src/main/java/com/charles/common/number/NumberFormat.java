package com.charles.common.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberFormat {

    public static void main(String[] args) {
        final DecimalFormat format = new DecimalFormat(".##");
        System.out.println(format.format(12.3456789));

        final String SPEED_FORMATTER = "%.2f";
        System.out.println(String.format(SPEED_FORMATTER, 234.12312412));

        BigDecimal bigDecimal = new BigDecimal(111231.5585);
        System.out.println(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }
}
