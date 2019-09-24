package com.charles.pattern.dynamicproxy;

import com.charles.pattern.dynamicproxy.impl.Calculator;
import com.charles.pattern.dynamicproxy.impl.CalculatorImpl;
import com.charles.pattern.dynamicproxy.impl.HelloWorld;
import com.charles.pattern.dynamicproxy.impl.HelloWorldImpl;

import java.lang.reflect.Proxy;

import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * @author Charles
 */
public class ProxyMain {

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorldImpl();
        JdkProxyHandler jdkProxyHandler = new JdkProxyHandler(helloWorld);
        HelloWorld helloWorldProxy = (HelloWorld) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                helloWorld.getClass().getInterfaces(), jdkProxyHandler);
        helloWorldProxy.sayHello();

        hyphenSeparator();

        Calculator calculator = new CalculatorImpl();
        jdkProxyHandler = new JdkProxyHandler(calculator);
        // JDK动态代理只能针对实现了接口的类进行代理，newProxyInstance 函数所需参数就可看出
        Calculator calculatorHandler = (Calculator) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                calculator.getClass().getInterfaces(), jdkProxyHandler);
        calculatorHandler.add(2, 3);

        CglibProxyHandler cglibProxyHandler = new CglibProxyHandler(calculator);
        ((Calculator) cglibProxyHandler.getCglibProxy()).add(4, 5);
    }
}
