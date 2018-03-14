package com.charles.utils;

import java.util.regex.Pattern;

import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * Pattern / Matcher
 * find()方法，用来搜索与正则表达式相匹配的任何目标字符串，
 * group()方法，用来返回包含了所匹配文本的字符串
 */
public class RegExprHelper {
    private static final Pattern PHONE_NUMBER = Pattern.compile("^((1[358][0-9])|(14[57])|(17[013678]))\\d{8}$");
    private static final Pattern PASSWORD = Pattern.compile("^(\\w){6,16}$");
    private static final Pattern ACCOUNT = Pattern.compile("^[a-zA-z]\\w{3,15}$");
    private static final Pattern CODE_REG = Pattern.compile("^(\\w){1,20}$");
    private static final Pattern TELEPHONE = Pattern.compile("^((0[12][0-9])|(0[3-9][1-9][0-9]))-?\\d{7,8}$");
    private static final Pattern EMAIL = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    private static boolean validate(Pattern pattern, String str) {
        return pattern.matcher(str).find();
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        return validate(PHONE_NUMBER, phoneNumber);
    }

    public static boolean isPassword(String password) {
        return validate(PASSWORD, password);
    }

    public static boolean isAccount(String account) {
        return validate(ACCOUNT, account);
    }

    public static boolean isTelephone(String telephone) {
        return validate(TELEPHONE, telephone);
    }

    public static boolean isEmail(String email) {
        return validate(EMAIL, email);
    }

    public static boolean isCode(String code) {
        return validate(CODE_REG, code);
    }

    public static void main(String[] args) {
        hyphenSeparator("Password");
        System.out.println(isPassword("12345"));
        System.out.println(isPassword("123456"));
        //
        System.out.println(isPassword("123456chaRles"));
        System.out.println(isPassword("123456_chaRles"));
        System.out.println(isPassword("_123456_chaRles"));
        System.out.println(isPassword("123456_chaRles_"));
        //
        System.out.println(isPassword("123456%charles"));
        System.out.println(isPassword("123456_charles_ABC"));
    }
}
