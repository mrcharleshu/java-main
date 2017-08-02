package com.charles.utils;

public final class LineSeparators {

    public static void startSeparator() {
        // System.out.println("***************************************************************");
        // System.out.println("·······································································");
        System.out.println("·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·");
    }

    public static void hyphenSeparator() {
        System.out.println("-------------------------------------------");
    }

    public static void hyphenSeparator(String title) {
        System.out.println("--------------------- " + title + " ----------------------");
    }
}