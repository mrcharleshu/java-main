package com.charles.spring.bpp1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <a href="https://chowdera.com/2022/157/202206061938253850.html">for example: not eligible for auto-proxying问题排查</a>
 */
public class BeanPostProcessorInitOrderTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.charles.spring.bpp1");
        DataHolder dataHolder = applicationContext.getBean(DataHolder.class);
        System.out.println("myComponent.getName() = " + dataHolder.getName());
    }
}