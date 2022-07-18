package com.charles.spring.bpp1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SetNameBeanPostProcessor implements BeanPostProcessor, Ordered {

    public SetNameBeanPostProcessor() {
        log.info("Initializing...");
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("setName...");
        if (bean instanceof DataHolder) {
            ((DataHolder) bean).setName("Charles");
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}