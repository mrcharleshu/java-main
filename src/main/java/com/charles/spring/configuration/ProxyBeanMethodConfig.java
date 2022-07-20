package com.charles.spring.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * proxyBeanMethods默认为true,表示cglib会为@Configuration生成一个代理类，方法之间调用会从IOC容器去获取实例对象。
 * 因此而在list1中调用hashMap方法时，会通过代理方法从IOC容器中去获取，这样就是单例的。
 * 运行的时候，控制台只打印了一次“bashMap调用”就证明了这一点
 * <p>
 * 但是如果将proxyBeanMethods设为false，则表示不生成代理，
 * 那么list1中调用bashMap,会再生成一个对象而不是从IOC容器中获取，这样能提高性能，也造成了多例。
 */
@Slf4j
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {MultiProfileConfiguration.class, ProcessorConfiguration.class, BeanConfiguration.class, ConditionalConfiguration.class})})
@SpringBootApplication(proxyBeanMethods = true)
public class ProxyBeanMethodConfig {

    @Bean
    public Map<String, Object> hashMap() {
        log.info("Method hashMap invoked");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("key", "this is HashMap");
        return resultMap;
    }

    @Bean
    public List<String> list1() {
        log.info("method list1 invoked");
        Map<String, Object> map = hashMap();
        List<String> mapList = map.keySet().stream().map(row -> map.get(row).toString()).collect(Collectors.toList());
        List<String> resultList = new ArrayList<>();
        resultList.add("this is list1");
        resultList.addAll(mapList);
        return resultList;
    }

    @Bean
    public List<String> list2() {
        log.info("method list2 invoked");
        Map<String, Object> map = hashMap();
        List<String> mapList = map.keySet().stream().map(row -> map.get(row).toString()).collect(Collectors.toList());
        List<String> resultList = new ArrayList<>();
        resultList.add("this is list2");
        resultList.addAll(mapList);
        return resultList;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProxyBeanMethodConfig.class, args);
    }
}