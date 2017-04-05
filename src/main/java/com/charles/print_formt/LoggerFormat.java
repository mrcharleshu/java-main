package com.charles.print_formt;

import org.slf4j.LoggerFactory;

public class LoggerFormat {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerFormat.class);

    public static void main(String[] args) {
        logger.debug("This method cost millis : {}ms", 1000);
    }
}
