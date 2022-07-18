package com.charles.spring.bpp2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * create a custom RandomInt annotation.
 * We'll use it to annotate fields that should have a random integer from a specified range inserted into them:
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RandomInt {

    int min();

    int max();
}