package com.charles.pattern.dynamicproxy.impl;

/**
 * @author Charles
 */
public class CalculatorImpl implements Calculator {

    @Override
    public void add(final int x, final int y) {
        System.out.println(String.format("%s+%s=%s", x, y, x + y));
    }
}
