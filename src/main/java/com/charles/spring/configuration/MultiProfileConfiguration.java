package com.charles.spring.configuration;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * 该类相当于 application.xml文件
 */
@Configuration // 该配置是必须的
@ComponentScan("com.charles.spring") // @ImportResource("classpath:application.xml")
public class MultiProfileConfiguration {

    /**
     * @Profile注解相当于一个标记，标记当前的dataSource是开发环境下的dataSource
     */
    @Bean("ds")
    @Profile("dev") // profile dev 设置 开发环境
    public DataSource devDs() {
        return new MysqlDataSource();
    }

    @Bean("ds")
    @Profile("pro") // profile Pro 设置生产环境
    public DataSource proDs() {
        return new MysqlDataSource();
    }
}