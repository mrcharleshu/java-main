package com.charles.netty.client;

import com.charles.netty.model.RequestData;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        RequestData msg = new RequestData();
        msg.setIntValue(123);
        msg.setStringValue("all work and no play makes jack a dull boy");
        log.info("NettyClient-RequestDataSentStart: {}", msg);
        ChannelFuture future = ctx.writeAndFlush(msg);
        future.addListener((ChannelFutureListener) channelFuture -> log.info("NettyClient-RequestDataSentComplete: {}", msg));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("NettyClient-ChannelRead: {}", msg);
        ctx.close();
    }
}