package com.charles.common.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * pattern特殊字符说明
 * “0” 指定位置不存在数字则显示为0  123.123 -> 0000.0000 -> 0123.1230
 * "#" 指定位置不存在数字则不显示 123.123 -> ####.#### -> 123.123
 * "."  小数点
 * "%" 会将结果数字乘以100 后面再加上% 123.123 -> #.00-> 1.3212%
 */
public class NumberFormat {
    public static void main(String[] args) {
        hyphenSeparator("First");
        java.text.DecimalFormat format1 = new java.text.DecimalFormat(".##");
        java.text.DecimalFormat format2 = new java.text.DecimalFormat("#.00");
        java.text.DecimalFormat format3 = new java.text.DecimalFormat("#.00");
        format3.setMaximumFractionDigits(2);
        java.text.DecimalFormat format4 = new java.text.DecimalFormat("0.#");
        format4.setMaximumFractionDigits(2);
        java.text.NumberFormat format5 = java.text.NumberFormat.getNumberInstance(Locale.CHINA);
        format5.setMaximumFractionDigits(2);
        format(format1, 0.00, 23, 12.3, 0.3456789, 12123124231421.1231231234123);
        hyphenSeparator("2");
        format(format2, 0.00, 23, 12.3, 0.3456789, 12123124231421.1231231234123);
        hyphenSeparator("3");
        format(format3, 0.00, 23, 12.3, 0.3456789, 12123124231421.1231231234123);
        hyphenSeparator("4");
        format(format4, 0.00, 23, 12.3, 0.3456789, 12123124231421.1231231234123);
        hyphenSeparator("5");
        format(format5, 0.00, 23, 12.3, 0.3456789, 12123124231421.1231231234123);
        hyphenSeparator("String formatter");
        stringFormat(0.00, 23, 12.3, 0.3456789, 12123124231421.1231231234123);
        hyphenSeparator("BigDecimal formatter");
        bigDecimalFormat(0.00, 23, 12.3, 0.3456789, 12123124231421.1231231234123);
    }

    private static void format(final DecimalFormat formatter, final double... values) {
        for (double value : values) {
            System.out.println(formatter.format(value));
        }
    }

    private static void format(final java.text.NumberFormat formatter, final double... values) {
        for (double value : values) {
            System.out.println(formatter.format(value));
        }
    }

    private static void stringFormat(final double... values) {
        final String SPEED_FORMATTER = "%.2f";
        for (double value : values) {
            System.out.println(String.format(SPEED_FORMATTER, value));
        }
    }

    private static void bigDecimalFormat(final double... values) {
        for (double value : values) {
            System.out.println(new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
    }
}
