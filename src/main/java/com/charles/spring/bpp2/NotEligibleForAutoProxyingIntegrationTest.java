package com.charles.spring.bpp2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RandomIntProcessor.class, DataCache.class, RandomIntGenerator.class})
public class NotEligibleForAutoProxyingIntegrationTest {

    private RandomIntProcessor randomIntProcessor;

    @Autowired
    private DataCache dataCache;

    @Test
    public void givenAutowireInBeanPostProcessor_whenSpringContextInitialize_thenNotEligibleLogShouldShow() {
        assertEquals(0, dataCache.getGroup());
    }
}