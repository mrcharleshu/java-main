package com.charles.common.collection;

import java.util.HashMap;
import java.util.Map;

public class JavaReferenceTest {
    public static class Student {
        private String name;
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String old) {
            this.age = old;
        }

        @Override
        public String toString() {
            return "Studeng{" +
                    "name='" + name + '\'' +
                    ", old='" + age + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        System.out.println(null + "");
        System.out.println("" + null);
        Student student = new Student();
        student.setName("yuanxindong");
        student.setAge("12");

        Map<String, Object> map = new HashMap<>();
        // student = new Student();
        student.setName("胡斌");
        student.setAge("6");
        map.put("1", student);
        // student = new Student();
        student.setName("yu6666");
        student.setAge("13");
        map.put("2", student);
        System.out.println(student);
        System.out.println(map);
    }
}
