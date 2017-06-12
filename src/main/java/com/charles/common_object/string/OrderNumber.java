package com.charles.common_object.string;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Random;

public class OrderNumber {

    public static void main(String[] args) {
        System.out.println(generateOrderSeno());
    }

    private static String generateOrderSeno() {
        StringBuilder sb = new StringBuilder("ORD");
        sb.append(DateFormatUtils.format(new Date(), "yyMMddHHmmssSSS"));
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                sb.append((char) (choice + random.nextInt(26)));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                sb.append(random.nextInt(10));
            }
        }
        return sb.toString();
    }
}
