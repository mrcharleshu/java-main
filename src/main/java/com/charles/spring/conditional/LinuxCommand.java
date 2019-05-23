package com.charles.spring.conditional;

public class LinuxCommand implements Command {

    @Override
    public String show() {
        return "Linux ls";
    }
}