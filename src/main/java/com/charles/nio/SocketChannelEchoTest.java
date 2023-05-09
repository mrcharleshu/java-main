package com.charles.nio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

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
        assertEquals("hello", client1Response1);
        assertEquals("world", client1Response2);
        assertEquals("hello", client2Response1);
        assertEquals("world", client2Response2);
    }

    @After
    public void teardown() throws IOException {
        System.out.println("teardown...");
        server.destroy();
        SocketChannelEchoClient.stop();
    }
}