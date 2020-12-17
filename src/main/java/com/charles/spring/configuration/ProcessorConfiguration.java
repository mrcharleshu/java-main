package com.charles.spring.configuration;

import com.charles.spring.processor.CommandProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessorConfiguration {

    @Bean
    public CommandProcessor commandProcessor(ApplicationContext context) {
        System.out.println("Create command processor bean");
        return new CommandProcessor(context);
    }

}
