package com.charles.thread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author Charles
 */
public class UnsafeTest {

    private static class Person {
        private final String name;

        private Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) throws Exception {
        String bootClassPath = System.getProperty("sun.boot.class.path");
        for (String classpath : bootClassPath.split(":")) {
            System.out.println(classpath);
        }
        Field getUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        getUnsafe.setAccessible(true);
        Unsafe UNSAFE = (Unsafe) getUnsafe.get(null);
        // Unsafe UNSAFE = Unsafe.getUnsafe();
        // 创建对象实例
        Person person = (Person) UNSAFE.allocateInstance(Person.class);
        // 操作对象属性
        Field nameField = Person.class.getDeclaredField("name");
        long nameFieldOffset = UNSAFE.objectFieldOffset(nameField);
        UNSAFE.putObject(person, nameFieldOffset, "Charles");
        System.out.println(person.getName());
        // 操作数组
        // String[] strings = new String[]{"1", "2", "3"};
        // long i = UNSAFE.arrayBaseOffset(String[].class);
        // 操作内存
        // long address = UNSAFE.allocateMemory(8L);
    }
}
