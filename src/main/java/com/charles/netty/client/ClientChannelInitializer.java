package com.charles.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslContext;

    public ClientChannelInitializer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // pipeline.addLast(new SslHandler(sslEngine));
        pipeline.addLast(sslContext.newHandler(ch.alloc()));
        // On top of the SSL handler, add the text line codec.
        // pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(new ClientRequestEncoder());
        pipeline.addLast(new ClientResponseDecoder());
        // and then business logic.
        pipeline.addLast(new ClientHandler());
    }
}
