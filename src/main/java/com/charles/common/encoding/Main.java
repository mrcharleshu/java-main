package com.charles.common.encoding;

public class Main {
    public static void main(String[] args) {
        String secret = "Hello World";
        String output = "JxF12TrwUP45BMd";
        System.out.println(Base58.encode(secret.getBytes()));
        System.out.println(new String(Base58.decode(output)));
    }
}
