package com.charles.spring.bpp2;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Create the RandomIntGenerator class.
 * It's a Spring component that we'll use to insert random int values into fields annotated by the RandomInt annotation:
 */
@Component
public class RandomIntGenerator {
    private Random random = new Random();
    private DataCache dataCache;

    public RandomIntGenerator(DataCache dataCache) {
        this.dataCache = dataCache;
    }

    public int generate(int min, int max) {
        return random.nextInt(max - min) + min;
    }
}