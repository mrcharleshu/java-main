package com.charles.common.clone;

import java.io.*;

import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * 使用Serializable同样可以做到对象的clone。但是：
 * Cloneable本身就是为clone设计的，虽然有一些缺点，但是如果它可以clone的话无疑用它来做clone比较合适。
 * 如果不行的话用原型构造函数，或者静态copy方法也可以。
 * <p>
 * Serializable制作clone的话，添加了太多其它的东西，增加了复杂性。
 * 1、所有的相关的类都得支持Serializable。这个相比支持Cloneable只会工作量更大
 * 2、Serializable添加了更多的意义，除了提供一个方法用Serializable制作Clone，该类等于也添加了其它的public API，
 * 如果一个类实现了Serializable，等于它的2进制形式就已经是其API的一部分了，不便于该类以后内部的改动。
 * 3、当类用Serializable来实现clone时，用户如果保存了一个老版本的对象2进制，该类升级，用户用新版本的类反系列化该对象，
 * 再调用该对象用Serializable实现的clone。这里为了一个clone的方法又引入了类版本兼容性的问题。不划算。
 */
public class Student implements Cloneable, Serializable {
    private String name;
    //Contained object
    private Subject subject;

    public Student(String name, Subject subject) {
        this.name = name;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // shallow copy
        return super.clone();
        // deep copy
        // Student student = (Student) super.clone();
        // student.subject = (Subject) student.getSubject().clone();
        // return student;
    }

    @Override
    public String toString() {
        return "Student{" + "name='" + name + '\'' + ", subject=" + subject.getName() + '}';
    }

    private static void rawClone() throws CloneNotSupportedException {
        //Original Object
        Subject subject = new Subject("Algebra");
        Student student = new Student("John", subject);
        System.out.println("Original Object: " + student);

        //Clone Object
        Student clonedStudent = (Student) student.clone();
        System.out.println("Cloned Object: " + clonedStudent);

        System.out.println(student == clonedStudent);
        System.out.println(student.getSubject() == clonedStudent.getSubject());

        student.setName("Dan");
        student.getSubject().setName("Physics");
        System.out.println("Original Object after it is updated: " + student);
        System.out.println("Cloned Object after updating original object: " + clonedStudent);
        System.out.println(student == clonedStudent);
        System.out.println(student.getSubject() == clonedStudent.getSubject());
    }

    private static void serializableClone() throws CloneNotSupportedException {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            // create original serializable object
            Subject subject = new Subject("Algebra");
            Student student1 = new Student("Charles", subject);
            // print it
            System.out.println("Original = " + student1);

            Student student2;
            // deep copy
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            // serialize and pass the object
            oos.writeObject(student1);
            oos.flush();
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);
            // return the new object
            student2 = (Student) ois.readObject();

            // verify it is the same
            System.out.println("Copied   = " + student2);
            System.out.println("Is Equal = " + (student1 == student2));
            // change the original object's contents
            student1.setName("Sonya");
            student1.getSubject().setName("English");
            // see what is in each one now
            System.out.println("Original = " + student1);
            System.out.println("Copied   = " + student2);
            System.out.println("Is Equal = " + (student1 == student2));
        } catch (Exception e) {
            System.out.println("Exception in main = " + e);
        } finally {
            try {
                oos.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        hyphenSeparator("rawClone");
        rawClone();
        hyphenSeparator("serializableClone");
        serializableClone();
    }
}