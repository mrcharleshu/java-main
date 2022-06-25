package com.charles.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 1、配置hosts将域名解析到localhost上：127.0.0.1 www.mrcharleshu.com
 * 2、启动本服务（增加VM options：-Djavax.net.debug=ssl,handshake）
 * 3、由于浏览器就是最天然的HTTPS客户端，这里可以使用浏览器来测试。浏览器输入：
 * https://www.mrcharleshu.com:8080/?name=Charles
 */
public class FastNettyServer {

    public static InputStream getInputStream(String filename) throws FileNotFoundException {
        // return FastNettyServer.class.getClassLoader().getResourceAsStream("ca/" + filename);
        String dir = "/Users/Charles/Downloads/cacerts/www.mrcharleshu.com/";
        return new FileInputStream(dir + filename);
    }

    public static void main(String[] args) throws InterruptedException, SSLException, FileNotFoundException {
        InputStream certificate = getInputStream("www.mrcharleshu.com.pem");  // 证书
        InputStream privateKey = getInputStream("private.pem");  // 私钥
        final SslContext sslContext = SslContextBuilder.forServer(certificate, privateKey).build();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

                    // 加入SslHandler实现HTTPS
                    SslHandler sslHandler = sslContext.newHandler(ch.alloc());
                    pipeline.addLast(sslHandler);

                    pipeline.addLast(new HttpServerCodec());
                    pipeline.addLast(new HttpServerHandler());
                }
            });
            ChannelFuture f = b.bind(8080).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Slf4j
    static class HttpServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
            if (msg instanceof HttpRequest) {
                log.info("HttpRequest Accepted.");
                // 请求，解码器将请求转换成HttpRequest对象
                HttpRequest request = (HttpRequest) msg;

                // 获取请求参数
                QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
                List<String> nameQueryValue = queryStringDecoder.parameters().get("name");
                String name = "World";
                if (nameQueryValue != null) {
                    name = nameQueryValue.get(0);
                }

                // 响应HTML
                String responseMsg = "Hello, " + name + "! now is " + LocalDateTime.now();
                String responseHtml = "<html><body>" + responseMsg + "</body></html>";
                byte[] responseBytes = responseHtml.getBytes(StandardCharsets.UTF_8);
                int contentLength = responseBytes.length;

                // 构造FullHttpResponse对象，FullHttpResponse包含message body
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(responseBytes));
                response.headers().set("Content-Type", "text/html; charset=utf-8");
                response.headers().set("Content-Length", Integer.toString(contentLength));

                ctx.writeAndFlush(response);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
