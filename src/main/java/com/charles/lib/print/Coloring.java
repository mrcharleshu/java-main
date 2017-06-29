package com.charles.lib.print;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Coloring {

    private static final Logger logger = LoggerFactory.getLogger(Coloring.class);

    public static void main(String[] args) {
        logger.error("Hello world, Logback");
        logger.warn("Hello world, Logback");
        logger.info("Hello world, Logback");
        logger.debug("Hello world, Logback");
    }
}
