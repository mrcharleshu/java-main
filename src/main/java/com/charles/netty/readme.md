[Introduction to Netty](https://www.baeldung.com/netty)

**服务启动后日志情况：**
```log
11:42:19.759 [nioEventLoopGroup-2-1] INFO com.charles.netty.client.ClientHandler - NettyClient-RequestDataSentStart: RequestData(intValue=123, stringValue=all work and no play makes jack a dull boy)

11:42:19.780 [nioEventLoopGroup-3-2] INFO com.charles.netty.server.ServerHandler - NettyServer-ChannelRead: RequestData(intValue=123, stringValue=all work and no play makes jack a dull boy)

11:42:19.832 [nioEventLoopGroup-2-1] INFO com.charles.netty.client.ClientHandler - NettyClient-RequestDataSentComplete: RequestData(intValue=123, stringValue=all work and no play makes jack a dull boy)
11:42:19.836 [nioEventLoopGroup-2-1] INFO com.charles.netty.client.ClientHandler - NettyClient-ChannelRead: ResponseData(intValue=246)

11:42:19.781 [nioEventLoopGroup-3-2] INFO com.charles.netty.server.ServerHandler - NettyServer-ResponseDataSentComplete: ResponseData(intValue=246)
```