package com.charles.pattern.dynamicproxy;

public class HelloWorldImpl implements HelloWorld {
    @Override
    public void sayHello() {
        System.out.println("Hello World, My name is Charles");
    }
}
