package com.charles.common.number;

import java.util.Optional;
import java.util.regex.Pattern;

public class NumberJudge {

    public static void main(String[] args) {
        System.out.println(isStrNumber1("123"));
        System.out.println(isStrNumber1("code"));
        System.out.println(isStrNumber2("123"));
        System.out.println(isStrNumber2("code"));
    }

    private static boolean isStrNumber1(String str) {
        try {
            return Optional.ofNullable(str).map(Integer::parseInt).isPresent();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isStrNumber2(String str) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        return pattern.matcher(str).matches();
    }
}
