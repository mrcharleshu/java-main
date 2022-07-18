package com.charles.spring.bpp1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataHolder implements Ordered {

    private String name;

    public DataHolder() {
        log.info("Initializing...");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}