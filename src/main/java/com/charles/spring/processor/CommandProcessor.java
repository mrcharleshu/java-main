package com.charles.spring.processor;

import com.charles.spring.conditional.Command;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;

/**
 * https://www.jianshu.com/p/fb39f568cd5e
 * https://www.dazhuanlan.com/2019/10/22/5daebc5d16429/
 */
public class CommandProcessor implements BeanPostProcessor, InitializingBean, DisposableBean, Ordered {
    private final ApplicationContext context;

    public CommandProcessor(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Command) {
            System.out.println("[Before] matched beanName is: " + beanName + ",result is: " + ((Command) bean).show());
        } else {
            System.out.println("[Before] beanName is: " + beanName);
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Command) {
            System.out.println("[After] matched beanName is: " + beanName + ",result is: " + ((Command) bean).show());
        } else {
            System.out.println("[After] beanName is: " + beanName);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("CommandProcessor after properties set");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("CommandProcessor destroy");
    }
}
