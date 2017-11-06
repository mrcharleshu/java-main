package com.charles.pattern.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggerHandler implements InvocationHandler {
    private Object target;

    public LoggerHandler(final Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        System.out.println("My logic start...");
        method.invoke(target, args);
        System.out.println("My logic end...");
        return null;
    }
}
