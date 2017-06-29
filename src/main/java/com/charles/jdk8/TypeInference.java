package com.charles.jdk8;

public class TypeInference {

    public static void main(String[] args) {
        final Value<String> value = new Value<>();
        String result = value.getOrDefault("22", Value.defaultValue());
        System.out.println(result);
    }
}

class Value<T> {

    public static <T> T defaultValue() {
        return null;
    }

    public T getOrDefault(T value, T defaultValue) {
        return (value != null) ? value : defaultValue;
    }
}