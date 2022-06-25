package com.charles.netty.server;

import com.charles.netty.FastNettyServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import javax.net.ssl.SSLException;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class NettyServer {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 9000;
    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : PORT;
        new NettyServer(port).run();
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            final SslContext sslContext = getServerSslContext();
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ServerChannelInitializer(sslContext));
            b.option(ChannelOption.SO_BACKLOG, 128);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    private SslContext getServerSslContext() throws FileNotFoundException, SSLException {
        InputStream certificate = FastNettyServer.getInputStream("www.mrcharleshu.com.pem");  // 证书
        InputStream privateKey = FastNettyServer.getInputStream("private.pem");  // 私钥
        return SslContextBuilder.forServer(certificate, privateKey).build();
    }
}
