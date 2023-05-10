package com.charles.nio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * <a href="https://www.baeldung.com/java-nio-selector">Introduction to the Java NIO Selector</a>
 *
 * @author charles
 */
public class SocketChannelEchoTest {

    private Process server;
    private SocketChannelEchoClient client1;
    private SocketChannelEchoClient client2;

    @Before
    public void setup() throws IOException, InterruptedException {
        System.out.println("setup...");
        server = SocketChannelEchoServer.start();
        TimeUnit.SECONDS.sleep(1);
        client1 = SocketChannelEchoClient.newInstance();
        client2 = SocketChannelEchoClient.newInstance();
    }

    @Test
    public void givenServerClient_whenServerEchosMessage_thenCorrect() {
        String client1Response1 = client1.sendMessage("hello");
        String client1Response2 = client1.sendMessage("world");
        String client2Response1 = client2.sendMessage("hello");
        String client2Response2 = client2.sendMessage("world");
        assertTrue(client1Response1.contains("hello"));
        assertTrue(client1Response2.contains("world"));
        assertTrue(client2Response1.contains("hello"));
        assertTrue(client2Response2.contains("world"));
    }

    @After
    public void teardown() throws IOException {
        System.out.println("teardown...");
        server.destroy();
        client1.stop();
        client2.stop();
    }
}