package com.charles.common;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.charles.utils.LineSeparators.hyphenSeparator;

public class ReflectCommon {

    public static void main(String[] args) {
        createObject();
        hyphenSeparator();
        methodInvoke1();
        hyphenSeparator();
        System.out.println(toCamelCase("update_time"));
        System.out.println(toCamelCaseLC("update_time"));
        Student student = new Student("Charles", 25);
        System.out.println(methodInvoke2(student, "name"));
        hyphenSeparator();
        System.out.println(methodInvoke3(student, "name"));
    }

    private static class Student {
        private String name;
        private Integer age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    /**
     * 如何通过反射创建对象
     * - 方法1：通过类对象调用newInstance()方法，例如：String.class.newInstance()
     * - 方法2：通过类对象的getConstructor()或getDeclaredConstructor()方法获得构造器（Constructor）对象并调用其newInstance()方法创建对象，
     */
    public static void createObject() {
        try {
            String str1 = String.class.newInstance().concat("Hello");
            String str2 = String.class.getConstructor(String.class).newInstance("World");
            System.out.println(str1);
            System.out.println(str2);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void methodInvoke1() {
        String str = "Charles";
        Method m;
        try {
            m = str.getClass().getMethod("toUpperCase");
            System.out.println(m.invoke(str));  // HELLO
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Object methodInvoke2(Student entity, String fieldName) {
        Object returnValue = null;
        try {
            Class<?> clazz = entity.getClass();
            Method[] methods = clazz.getDeclaredMethods();
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            System.out.println("MethodName: " + methodName);
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Method m = clazz.getMethod(method.getName());
                    returnValue = m.invoke(entity);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public static Object methodInvoke3(Student entity, String fieldName) {
        String methodName = "get" + toCamelCase(fieldName);
        System.out.println("MethodName: " + methodName);
        Method method = ReflectionUtils.findMethod(entity.getClass(), methodName);
        // return ReflectionUtils.invokeMethod(method, entity);
        Object returnValue = null;
        try {
            returnValue = method.invoke(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    /**
     * Convert a string to camel case
     * @param string
     * @return
     */
    public static String toCamelCase(String string) {
        StringBuilder result = new StringBuilder();
        // [#2515] - Keep trailing underscores
        for (String word : string.split("_", -1)) {
            // Uppercase first letter of a word
            if (word.length() > 0) {
                // [#82] - If a word starts with a digit, prevail the
                // underscore to prevent naming clashes
                if (Character.isDigit(word.charAt(0))) {
                    result.append("_");
                }
                result.append(word.substring(0, 1).toUpperCase());
                result.append(word.substring(1).toLowerCase());
            }
            // If no letter exists, prevail the underscore (e.g. leading // underscores)
            else {
                result.append("_");
            }
        }
        return result.toString();
    }

    /**
     * Convert a string to camel case starting with a lower case letter
     */
    public static String toCamelCaseLC(String string) {
        return toLC(toCamelCase(string));
    }

    /**
     * Change a string's first letter to lower case
     */
    public static String toLC(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        return Character.toLowerCase(string.charAt(0)) + string.substring(1);
    }
}
