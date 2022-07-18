package com.charles.spring.bpp1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 因为当前类的初始化顺序在{@link SetNameBeanPostProcessor}之前
 * 所以如果依赖{@link DataHolder}，必定先初始化{@link DataHolder}
 * </p>
 * 但此时{@link SetNameBeanPostProcessor}还未实例化
 * 因此无法执行{@link SetNameBeanPostProcessor#postProcessAfterInitialization(Object, String)}
 */
@Slf4j
@Component
public class AheadSetNameBeanPostProcessor implements BeanPostProcessor, Ordered {

    private DataHolder dataHolder;

    public AheadSetNameBeanPostProcessor() {
        log.info("Initializing...");
    }

    @Lazy
    @Autowired
    public void setMyComponent(DataHolder dataHolder) {
        log.info("set property...");
        this.dataHolder = dataHolder;
    }

    // @PostConstruct
    // public void test() {
    //     log.info("PostConstruct...");
    // }
    //
    // @Bean
    // public String another() {
    //     log.info("Build Another Component...");
    //     return "Another";
    // }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("postProcessAfterInitialization...");
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}