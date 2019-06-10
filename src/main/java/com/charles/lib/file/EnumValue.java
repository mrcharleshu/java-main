package com.charles.lib.file;

public interface EnumValue {

    String value();

    default boolean isValueEqual(String anotherValue) {
        return value().equals(anotherValue);
    }
}
