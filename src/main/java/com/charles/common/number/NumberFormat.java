package com.charles.common.number;

import java.math.BigDecimal;

import static com.charles.utils.LineSeparators.hyphenSeparator;

public class NumberFormat {
    public static void main(String[] args) {
        hyphenSeparator("First");
        java.text.DecimalFormat format1 = new java.text.DecimalFormat(".##");
        java.text.DecimalFormat format2 = new java.text.DecimalFormat("#.00");
        java.text.DecimalFormat format3 = new java.text.DecimalFormat("#.00");
        format3.setMaximumFractionDigits(2);
        java.text.DecimalFormat format4 = new java.text.DecimalFormat();
        format4.setMaximumFractionDigits(2);
        java.text.NumberFormat format5 = java.text.NumberFormat.getNumberInstance();
        format5.setMaximumFractionDigits(2);
        System.out.println(format1.format(12.3456789));
        System.out.println(format1.format(0.3456789));
        System.out.println(format1.format(12));
        hyphenSeparator("2");
        System.out.println(format2.format(12.3456789));
        System.out.println(format2.format(0.3456789));
        System.out.println(format2.format(12));
        hyphenSeparator("3");
        System.out.println(format3.format(12.3456789));
        System.out.println(format3.format(0.3456789));
        System.out.println(format3.format(12));
        hyphenSeparator("4");
        System.out.println(format4.format(23.5455));
        System.out.println(format4.format(0.5455));
        System.out.println(format4.format(23));
        hyphenSeparator("5");
        System.out.println(format5.format(23.5455));
        System.out.println(format5.format(0.5455));
        System.out.println(format5.format(0.00));
        hyphenSeparator("Second");
        final String SPEED_FORMATTER = "%.2f";
        System.out.println(String.format(SPEED_FORMATTER, 234.12312412));
        System.out.println(String.format(SPEED_FORMATTER, 0.12412412));
        System.out.println(String.format(SPEED_FORMATTER, 0.12612412));
        hyphenSeparator("Third");
        System.out.println(new BigDecimal(111231).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        System.out.println(new BigDecimal(0.111231).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }
}
