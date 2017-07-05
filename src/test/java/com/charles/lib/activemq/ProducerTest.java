package com.charles.lib.activemq;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.jms.JMSException;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.AssertionErrors.fail;

public class ProducerTest {

    private static Producer producer;
    private static Consumer consumer;

    @BeforeClass
    public static void setUpBeforeClass() throws JMSException {
        String destinationName = "helloworld.q";
        producer = new Producer();
        producer.create(destinationName);

        consumer = new Consumer();
        consumer.create(destinationName);
    }

    @AfterClass
    public static void tearDownAfterClass() throws JMSException {
        producer.close();
        consumer.close();
    }

    @Test
    public void testGetGreeting() {
        try {
            producer.sendName("John", "Doe");
            String greeting = consumer.getGreeting(1000);
            assertEquals("Hello John Doe!", greeting);
        } catch (JMSException e) {
            fail("a JMS Exception occurred");
        }
    }

    @Test
    public void testNoGreeting() {
        try {
            String greeting = consumer.getGreeting(1000);
            assertEquals("no greeting", greeting);
        } catch (JMSException e) {
            fail("a JMS Exception occurred");
        }
    }
}