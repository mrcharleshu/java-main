package com.charles.spring.binder;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author charles
 */
@Data
public class User {

    private String name;

    private Integer age;

    private LocalDateTime birthday;
}
