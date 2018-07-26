package com.charles.common.encrypt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;

public final class SpringSecurityEncrypt {
    public static String encryptPassword(final String password) {
        if (StringUtils.isBlank(password)) {
            return null;
        }
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(final String password, final String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public static void main(String[] args) {
        String password = "mrcharleshu";
        String pwd1 = encryptPassword(password);
        String pwd2 = encryptPassword(password);
        System.out.println(String.format("password1 = %s", pwd1));
        System.out.println(String.format("password2 = %s", pwd2));
        System.out.println(checkPassword(password, pwd1));
        System.out.println(checkPassword(password, pwd2));
    }
}
