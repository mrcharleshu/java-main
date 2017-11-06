package com.charles.pattern.dynamicproxy;

import java.lang.reflect.Proxy;

import static com.charles.utils.LineSeparators.hyphenSeparator;

public class DynamicProxyTest {

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorldImpl();
        LoggerHandler loggerHandler = new LoggerHandler(helloWorld);
        HelloWorld helloWorldProxy = (HelloWorld) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                helloWorld.getClass().getInterfaces(), loggerHandler);
        helloWorldProxy.sayHello();

        hyphenSeparator();

        Calculator calculator = new CalculatorImpl();
        loggerHandler = new LoggerHandler(calculator);
        Calculator calculatorHandler = (Calculator) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                calculator.getClass().getInterfaces(), loggerHandler);
        calculatorHandler.add(2, 3);
    }
}
