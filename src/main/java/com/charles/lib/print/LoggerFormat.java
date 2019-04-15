package com.charles.lib.print;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.helpers.BasicMarkerFactory;

public class LoggerFormat {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFormat.class);

    public static void main(String[] args) {
        Marker marker = new BasicMarkerFactory().getMarker("charles-marker:");
        logger.debug(marker, "This method cost millis : {}ms", 1000);
        logger.info(marker, "Hello World");
    }
}
