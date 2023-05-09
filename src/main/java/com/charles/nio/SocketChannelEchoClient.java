package com.charles.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * <a href="https://www.baeldung.com/java-nio-selector">Introduction to the Java NIO Selector</a>
 *
 * @author charles
 */
public class SocketChannelEchoClient {
    private static SocketChannel client;
    private static ByteBuffer buffer;

    public static SocketChannelEchoClient newInstance() {
        return new SocketChannelEchoClient();
    }

    public static void main(String[] args) throws InterruptedException {
        SocketChannelEchoClient[] clients = new SocketChannelEchoClient[3];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = newInstance();
        }
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String command = scanner.next();
            String[] commands = command.split("=");
            int clientIndex = Integer.parseInt(commands[0]);
            String sendMessage = commands[1];
            clients[clientIndex].sendMessage(String.format("client %s: %s", clientIndex, sendMessage));
        }
    }

    public static void stop() throws IOException {
        client.close();
        buffer = null;
    }

    private SocketChannelEchoClient() {
        try {
            client = SocketChannel.open(new InetSocketAddress(SocketChannelEchoServer.SERVER_IP, SocketChannelEchoServer.SERVER_PORT));
            buffer = ByteBuffer.allocate(256);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String msg) {
        buffer = ByteBuffer.wrap(msg.getBytes());
        String response = null;
        try {
            long start = System.currentTimeMillis();
            client.write(buffer);
            buffer.clear();
            client.read(buffer);
            response = new String(buffer.array()).trim();
            long elapsed = System.currentTimeMillis() - start;
            System.out.printf("Client receive server response: %s, elapsed %dms%n", response, elapsed);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }
}