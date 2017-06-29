package com.charles.common.string;

import java.util.UUID;

/**
 * Java生成uuid是36位的，觉得太长肿么办，我们来把它变成22位的，uuid是有128位2进制，然后转化为32位16进制，
 * 在这个基础上在添加4个-,也就是把128位，每4位二进制数用0-9和a-f替换掉，
 * 那我们每6位二进制数转化 成a-z，A-Z，0-9加上-_刚好也能全部表示，我们把前面120位二进制数转化成20个字符，
 * 而后面八个二进制和原来一样转化为十六进制，这样就刚好22个字符，附上代码
 */
public class UUID22 {

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "-", "_"};

    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 每3个十六进制字符转换成为2个字符
        for (int i = 0; i < 10; i++) {
            String str = uuid.substring(i * 3, i * 3 + 3);
            //转成十六进制
            int x = Integer.parseInt(str, 16);
            //除64得到前面6个二进制数的
            shortBuffer.append(chars[x / 0x40]);
            // 对64求余得到后面6个二进制数1
            shortBuffer.append(chars[x % 0x40]);
        }
        //加上后面两个没有改动的
        shortBuffer.append(uuid.charAt(30));
        shortBuffer.append(uuid.charAt(31));
        return shortBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateShortUuid());
    }
}  