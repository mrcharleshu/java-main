package com.charles.spring.bpp2;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * create a DataCache class that is a simple Spring component.
 * We want to assign to cache a random group that might be used, for example, to support sharding.
 * To do that, we'll annotate that field with our custom annotation:
 */
@Data
@Component
public class DataCache {
    @RandomInt(min = 2, max = 10)
    private int group;
    private String name;
}