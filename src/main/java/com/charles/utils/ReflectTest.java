package com.charles.utils;

import org.jooq.tools.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Student {
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

public class ReflectTest {
    public static Object test1(Student entity, String fieldName) {
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

    public static Object test2(Student entity, String fieldName) {
        String methodName = "get" + StringUtils.toCamelCase(fieldName);
        System.out.println("MethodName: " + methodName);
        Method method = ReflectionUtils.findMethod(entity.getClass(), methodName);
//        return ReflectionUtils.invokeMethod(method, entity);
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

    public static void main(String[] args) {
        System.out.println(StringUtils.toCamelCase("update_time"));
        System.out.println(StringUtils.toCamelCaseLC("update_time"));
        Student student = new Student("Charles", 25);
        System.out.println(test1(student, "name"));
        System.out.println("################################################");
        System.out.println(test2(student, "name"));
    }
}
