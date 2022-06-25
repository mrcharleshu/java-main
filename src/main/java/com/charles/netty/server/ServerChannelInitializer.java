package com.charles.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslContext;

    public ServerChannelInitializer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 加入SslHandler实现HTTPS
        pipeline.addLast(sslContext.newHandler(ch.alloc()));
        // pipeline.addLast(new SslHandler(sslEngine));
        // pipeline.addLast(new CertParseHandler());
        // On top of the SSL handler, add the text line codec.
        // pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(new ServerRequestDecoder());
        pipeline.addLast(new ServerResponseEncoder());
        // and then business logic.
        pipeline.addLast(new ServerHandler());
    }
}
