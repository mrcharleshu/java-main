package com.charles.nio;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

/**
 * <a href="https://www.baeldung.com/java-nio-selector">Introduction to the Java NIO Selector</a>
 *
 * @author charles
 */
public class SocketChannelEchoServer {
    private static final String POISON_PILL = "POISON_PILL";
    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 5454;

    public static void main(String[] args) throws IOException, InterruptedException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(SERVER_IP, SERVER_PORT));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer = ByteBuffer.allocate(256);
        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                if (key.isAcceptable()) {
                    register(selector, serverSocket);
                }
                if (key.isReadable()) {
                    answerWithEcho(buffer, key);
                }
                iter.remove();
            }
        }
    }

    private static void answerWithEcho(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        String prepend = "server processed[" + LocalDateTime.now() + "], ";
        // add header string
        buffer.put(prepend.getBytes());
        // read into buffer. (channel >> buffer)
        int r = client.read(buffer);
        if (r == -1 || new String(buffer.array(), 0, buffer.position()).trim().equals(POISON_PILL)) {
            client.close();
            System.out.println("Server Not accepting client messages anymore");
        } else {
            String message = new String(buffer.array(), 0, buffer.position());
            String buffAll = new String(buffer.array()).trim();
            System.out.printf("Server receive message=[%s]%n", message);
            System.out.printf("Server receive buffAll=[%s]%n", buffAll);
            buffer.flip();
            // read from buffer into channel (channel << buffer)
            client.write(buffer);
            buffer.clear();
        }
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        SelectionKey sk = client.register(selector, SelectionKey.OP_READ);
        System.out.println("Server received new client: " + sk);
    }

    public static Process start() throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = SocketChannelEchoServer.class.getCanonicalName();
        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);
        return builder.start();
    }
}