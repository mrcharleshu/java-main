package com.charles.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 该类相当于 application.xml文件
 */
@Configuration
public class BeanConfiguration {

    @Bean("prototypeBook")
    @Scope("prototype")
    public Book getSingletonBook() {
        return new Book("Refactor");
    }

    /**
     * 该方法生成一个Book对象，和application.xml文件中的bean标签一致
     * 默认 id为方法名，可以通过name和value属性自定义
     * @return
     */
    @Bean("singletonBook")
    public Book getBook() {
        return new Book("Design Pattern");
    }

    public static class Book {
        private String name;

        public Book(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Book{" + "name='" + name + "\',object=" + super.toString() + '}';
        }
    }
}