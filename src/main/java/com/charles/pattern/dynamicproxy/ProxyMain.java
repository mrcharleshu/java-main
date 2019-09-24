package com.charles.pattern.dynamicproxy;

import com.charles.pattern.dynamicproxy.impl.Calculator;
import com.charles.pattern.dynamicproxy.impl.CalculatorImpl;
import com.charles.pattern.dynamicproxy.impl.HelloWorld;
import com.charles.pattern.dynamicproxy.impl.HelloWorldImpl;

import java.lang.reflect.Proxy;

import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * @author Charles
 * 一、原理区别：
 * <p>
 * java动态代理是利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理。
 * <p>
 * 而cglib动态代理是利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。
 * <p>
 * 1、如果目标对象实现了接口，默认情况下会采用JDK的动态代理实现AOP
 * 2、如果目标对象实现了接口，可以强制使用CGLIB实现AOP
 * <p>
 * 3、如果目标对象没有实现了接口，必须采用CGLIB库，spring会自动在JDK动态代理和CGLIB之间转换
 * <p>
 * 如何强制使用CGLIB实现AOP？
 * （1）添加CGLIB库，SPRING_HOME/cglib/*.jar
 * （2）在spring配置文件中加入<aop:aspectj-autoproxy proxy-target-class="true"/>
 * <p>
 * JDK动态代理和CGLIB字节码生成的区别？
 * （1）JDK动态代理只能对实现了接口的类生成代理，而不能针对类
 * （2）CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法
 * 因为是继承，所以该类或方法最好不要声明成final
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
