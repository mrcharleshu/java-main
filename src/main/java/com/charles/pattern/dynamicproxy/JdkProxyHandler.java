package com.charles.pattern.dynamicproxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Charles
 * JDK动态代理实现InvocationHandler接口
 */
@Slf4j
public class JdkProxyHandler implements InvocationHandler {
    private Object target;

    public JdkProxyHandler(final Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        log.info("JDK Proxy 动态代理开始...");
        Object result = method.invoke(target, args);
        log.info("JDK Proxy 动态代理结束，结果：{}", result);
        return result;
    }
}
