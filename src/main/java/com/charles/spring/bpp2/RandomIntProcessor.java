package com.charles.spring.bpp2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * <a href="https://www.baeldung.com/spring-not-eligible-for-auto-proxying">Solving Spring’s “not eligible for auto-proxying” Warning</a>
 * create a RandomIntProcessor class that will be responsible for finding fields
 * annotated with the RandomInt annotation and inserting random values into them:
 * <p>
 * the AOP auto-proxying mechanism is also the implementation of a BeanPostProcessor interface.
 * As a result, neither BeanPostProcessor implementations nor the beans they reference directly
 * are eligible for auto-proxying. What that means is that Spring's features that use AOP,
 * such as autowiring, security, or transactional annotations, won't work as expected in those classes.
 */
@Slf4j
public class RandomIntProcessor implements BeanPostProcessor {
    private final RandomIntGenerator randomIntGenerator;

    @Lazy
    public RandomIntProcessor(RandomIntGenerator randomIntGenerator) {
        this.randomIntGenerator = randomIntGenerator;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("Process bean: {}", beanName);
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            RandomInt injectRandomInt = field.getAnnotation(RandomInt.class);
            if (injectRandomInt != null) {
                int min = injectRandomInt.min();
                int max = injectRandomInt.max();
                int randomValue = randomIntGenerator.generate(min, max);
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, randomValue);
            }
        }
        return bean;
    }
}