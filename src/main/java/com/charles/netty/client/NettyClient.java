package com.charles.netty.client;

import com.charles.netty.server.NettyServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import javax.net.ssl.SSLException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 使用 SSL/TLS 加密 Netty 程序
 * https://waylau.com/essential-netty-in-action/CORE%20FUNCTIONS/Securing%20Netty%20applications%20with%20SSLTLS.html
 * <p>
 * <p>
 * <p>
 * Java 类io.netty.handler.ssl.SslContextBuilder 实例源码
 * https://codingdict.com/sources/java/io.netty.handler.ssl/71282.html
 * </p>
 * Java SslContextBuilder.forClient示例
 * https://java.hotexamples.com/zh/examples/io.netty.handler.ssl/SslContextBuilder/forClient/java-sslcontextbuilder-forclient-method-examples.html
 * </p>
 * Java SslContextBuilder.forClient方法代码示例
 * https://vimsky.com/examples/detail/java-method-io.netty.handler.ssl.SslContextBuilder.forClient.html
 */
public class NettyClient {

    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            final SslContext sslContext = getClientSslContext();
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ClientChannelInitializer(sslContext));

            ChannelFuture f = b.connect(NettyServer.HOST, NettyServer.PORT).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    private static InputStream getInputStream(String filename) throws FileNotFoundException {
        // return FastNettyServer.class.getClassLoader().getResourceAsStream("ca/" + filename);
        String dir = "/Users/Charles/Downloads/cacerts/api.mrcharleshu.com/";
        return new FileInputStream(dir + filename);
    }

    private static SslContext getClientSslContext() throws FileNotFoundException, SSLException {
        InputStream certificate = getInputStream("api.mrcharleshu.com.pem");  // 证书
        InputStream privateKey = getInputStream("private.pem");  // 私钥
        SslContextBuilder client = SslContextBuilder.forClient();
        // client.trustManager(InsecureTrustManagerFactory.INSTANCE);
        client.keyManager(certificate, privateKey);
        return client.build();
    }
}