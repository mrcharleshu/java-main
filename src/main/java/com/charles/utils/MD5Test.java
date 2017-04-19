package com.charles.utils;

import java.security.NoSuchAlgorithmException;

public class MD5Test {

    public static void main(String args[]) throws NoSuchAlgorithmException {
        String str1 = "{\"byFields\":[\"ES_EPG_CODE\",\"P_MARK\"],\"filters\":{\"conditions\":[{\"field\":\"ES_EPG_CODE\",\"function\":\"notEqual\",\"params\":[\"线路2\"]}],\"relation\":\"and\"},\"fromDate\":\"2017-04-01\",\"measures\":[{\"aggregator\":\"general\",\"eventName\":\"click\"}],\"project\":\"PKIPTV\",\"toDate\":\"2017-04-20\",\"unit\":\"day\"}";
        String str2 = "{\"byFields\":[\"P_TYPE\",\"P_MARK\"],\"filters\":{\"conditions\":[{\"field\":\"P_TYPE\",\"function\":\"notEqual\",\"params\":[\"线路4\"]}],\"relation\":\"and\"},\"fromDate\":\"2017-04-01\",\"measures\":[{\"aggregator\":\"general\",\"eventName\":\"click\"}],\"project\":\"PKIPTV\",\"toDate\":\"2017-04-19\",\"unit\":\"day\"}";
        String str3 = "8a683566bcc7801226b3d8b0cf35fd97";
        compare(str1, str2, str3);
    }

    public static void compare(String... strArr) throws NoSuchAlgorithmException {
        for (String str : strArr) {
            System.out.println("-------------------------------");
            System.out.println(MD5.getInstance().getMD5(str));
            System.out.println(CryptUtils.md5(str));
        }
    }
}
