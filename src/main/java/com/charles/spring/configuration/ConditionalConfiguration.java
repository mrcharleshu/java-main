package com.charles.spring.configuration;

import com.charles.spring.conditional.LinuxCommand;
import com.charles.spring.conditional.LinuxConditionShow;
import com.charles.spring.conditional.WindowsCommand;
import com.charles.spring.conditional.WindowsConditionShow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionalConfiguration {

    @Bean("command")
    @Conditional(LinuxConditionShow.class) // 关联条件设置
    public LinuxCommand showLinux() {
        System.out.println("Create Linux Command Bean");
        return new LinuxCommand();
    }

    @Bean("command")
    @Conditional(WindowsConditionShow.class) // 关联条件设置
    public WindowsCommand showWindows() {
        System.out.println("Create Windows Command Bean");
        return new WindowsCommand();
    }
}