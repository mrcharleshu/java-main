package com.charles.lib.print;

import org.slf4j.LoggerFactory;

public class LoggerFormat {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerFormat.class);

    public static void main(String[] args) {
        logger.debug("This method cost millis : {}ms", 1000);
    }
}
