package com.charles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class JavaMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaMainApplication.class, args);
    }

    @GetMapping(name = "index")
    public String index() {
        return "Hello world";
    }
}
