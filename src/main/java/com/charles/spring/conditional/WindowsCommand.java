package com.charles.spring.conditional;

public class WindowsCommand implements Command {

    @Override
    public String show() {
        return "Windows dir";
    }
}