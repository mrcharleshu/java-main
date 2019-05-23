package com.charles.spring;

import com.charles.spring.conditional.Command;
import com.charles.spring.configuration.BeanConfiguration;
import com.charles.spring.configuration.ConditionalConfiguration;
import com.charles.spring.configuration.MultiProfileConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.util.stream.IntStream;

import static com.charles.utils.LineSeparators.hyphenSeparator;

@SuppressWarnings("Duplicates")
public class ApplicationContextTester {
    private static final int BEAN_NUM = 5;

    private static void testBeanCreationFromAnnotation() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(BeanConfiguration.class);
        IntStream.range(0, BEAN_NUM).forEach(i -> {
            BeanConfiguration.Book book = ac.getBean("singletonBook", BeanConfiguration.Book.class);
            System.out.println(book);
        });
        hyphenSeparator();
        IntStream.range(0, BEAN_NUM).forEach(i -> {
            BeanConfiguration.Book book = ac.getBean("prototypeBook", BeanConfiguration.Book.class);
            System.out.println(book);
        });
    }

    private static void testBeanCreationFromXml() {
        // 获取ApplicationContext对象 加载配置文件 反射+xml解析
        ApplicationContext ac = new ClassPathXmlApplicationContext("application.xml");
        IntStream.range(0, BEAN_NUM).forEach(i -> {
            BeanConfiguration.Book book = ac.getBean("singletonBook", BeanConfiguration.Book.class);
            System.out.println(book);
        });
        hyphenSeparator();
        IntStream.range(0, BEAN_NUM).forEach(i -> {
            BeanConfiguration.Book book = ac.getBean("prototypeBook", BeanConfiguration.Book.class);
            System.out.println(book);
        });
    }

    private static void testMultiProfileBean() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        // 设置使用哪种环境  pro  dev
        ac.getEnvironment().setActiveProfiles("pro");
        ac.register(MultiProfileConfiguration.class);
        ac.refresh();
        DataSource ds = ac.getBean(DataSource.class);
        System.out.println(ds);
    }

    private static void testConditional() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        // 设置使用哪种环境 Linux和Window;
        ac.getEnvironment().setActiveProfiles("windows");
        ac.register(ConditionalConfiguration.class);
        ac.refresh();
        Command command = (Command) ac.getBean("command");
        System.out.println(command.show());
    }

    public static void main(String[] args) {
        testBeanCreationFromAnnotation();
        testBeanCreationFromXml();
        testMultiProfileBean();
        testConditional();
    }
}
