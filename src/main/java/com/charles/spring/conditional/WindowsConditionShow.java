package com.charles.spring.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WindowsConditionShow implements Condition {

    /**
     * 条件匹配的方法
     * true 条件匹配
     * false 条件不匹配
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String[] osName = context.getEnvironment().getActiveProfiles();
        for (String name : osName) {
            System.out.println(name);
            if (name.contains("windows")) {
                return true;
            }
        }
        return false;
    }
}