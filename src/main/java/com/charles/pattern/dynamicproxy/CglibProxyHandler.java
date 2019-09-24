package com.charles.pattern.dynamicproxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class CglibProxyHandler implements MethodInterceptor {

    private Object target;

    public CglibProxyHandler(final Object target) {
        this.target = target;
    }

    public Object getCglibProxy() {
        Enhancer enhancer = new Enhancer();
        //设置父类,因为Cglib是针对指定的类生成一个子类，所以需要指定父类
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("Cglib 动态代理开始...");
        //方法执行，参数：target 目标对象 arr参数数组
        Object result = method.invoke(target, objects);
        log.info("Cglib动态代理结束，结果：{}", result);
        return result;
    }
}
