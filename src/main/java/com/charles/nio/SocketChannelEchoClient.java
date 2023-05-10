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
    private SocketChannel client;
    private ByteBuffer buffer;

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
            try {
                String input = scanner.nextLine().trim();
                int splitIndex = input.indexOf("=");
                int clientIndex = Integer.parseInt(input.substring(0, splitIndex));
                String sendMessage = input.substring(splitIndex + 1);
                System.out.printf("Client parse input, input=[%s], splitIndex=[%d], client=[%d], sendMessage=[%s]%n",
                        input, splitIndex, clientIndex, sendMessage);
                clients[clientIndex].sendMessage(String.format("client_%s, %s", clientIndex, sendMessage));
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void stop() throws IOException {
        this.client.close();
        this.buffer = null;
    }

    private SocketChannelEchoClient() {
        try {
            client = SocketChannel.open(new InetSocketAddress(SocketChannelEchoServer.SERVER_IP,
                    SocketChannelEchoServer.SERVER_PORT));
            buffer = ByteBuffer.allocate(256);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String msg) {
        // "buffer = ByteBuffer.wrap(msg.getBytes())"
        buffer.put(msg.getBytes());
        try {
            long start = System.currentTimeMillis();
            // 需设置limit为了下面可写到channel
            buffer.flip();
            client.write(buffer);
            // 清空buffer为了下面可从channel中读
            buffer.clear();
            client.read(buffer);
            String message = new String(buffer.array(), 0, buffer.position());
            String buffAll = new String(buffer.array()).trim();
            long elapsed = System.currentTimeMillis() - start;
            System.out.printf("Client receive server message=[%s]%n", message);
            System.out.printf("Client receive server buffAll=[%s], elapsed %dms%n", buffAll, elapsed);
            buffer.clear();
            return message;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}