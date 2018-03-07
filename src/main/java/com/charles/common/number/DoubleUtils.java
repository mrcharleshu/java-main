package com.charles.common.number;

import java.math.BigDecimal;

public class DoubleUtils {
    public static void main(String[] args) {
        String i = numberToBits("10000000.01");
        System.out.println(i);
        System.out.println(999999.99 - 999990.00);
        System.out.println(sub(999999.99, 999990.00));
    }

    /**
     * double 相加
     * @param d1
     * @param d2
     * @return
     */
    public static double sum(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }

    /**
     * double 相减
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 乘法
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * double 除法
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return
     */
    public static double div(double d1, double d2, int scale) {
        // 当然在此之前，你要判断分母是否为0，为0你可以根据实际需求做相应的处理
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * double 转 string 去掉后面锝0
     * @param i
     * @return
     */
    public static String getString(double i) {
        String s = String.valueOf(i);
        if (s.indexOf(".") > 0) {
            //正则表达
            //去掉后面无用的零
            s = s.replaceAll("0+?$", "");
            //如小数点后面全是零则去掉小数点
            s = s.replaceAll("[.]$", "");
        }
        return s;
    }

    /**
     * 数字转换为千位符
     * @param number
     * @return
     */
    public static String numberToBits(String number) {
        String begin = "";
        String end = "";
        String[] num = number.split("\\.");
        if (num.length > 1) {
            begin = num[0];
            end = num[1];
        } else {
            begin = number;
        }
        return begin.replaceAll("(?<=\\d)(?=(?:\\d{3})+$)", ",") + "." + end;
    }

}