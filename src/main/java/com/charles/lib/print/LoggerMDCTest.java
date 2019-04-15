package com.charles.lib.print;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;

/**
 * MDC 上下文是以每个线程为基础进行管理的，允许每个服务器为线程设置不同的 MDC 标记。
 * 比如 put 和 get 之类的方法仅影响当前线程的 MDC 以及 当前线程的子线程，
 * 具体涉及到 ThreadLocal 和 InheritableThreadLocal 两个类，我们在使用 MDC 时不必担心线程安全性或同步问题。
 * </pre>
 * 这里我模拟了两个请求，当服务端接收到请求后，使用 MDC 保存了每个请求的 IP，并开启一个子线程来处理请求，
 * 通过打印日志，可以看到通过 MDC 能区分每个请求的日志，以及一个请求在多个线程中处理的日志。
 */
public class LoggerMDCTest {

    public static void runByApplication() {
        new ServerHandler("192.168.1.1").handleRequest();
        new ServerHandler("192.168.2.2").handleRequest();
    }

    // 服务器对请求的处理
    static class ServerHandler {
        private Logger logger = LoggerFactory.getLogger(ServerHandler.class);

        ServerHandler(String IP) {
            MDC.put("IP", IP); //将 IP 保存到 MDC 中
        }

        void handleRequest() {
            logger.info("before processing the request...");
            new Thread(new ServerService()).start();
            logger.info("after processing the request...");
            MDC.remove("IP");
        }
    }

    static class ServerService implements Runnable {
        private Logger logger = LoggerFactory.getLogger(ServerHandler.class);
        private Map<String, String> contextMap = MDC.getCopyOfContextMap(); // 获取 MDC 上下文副本

        @Override
        public void run() {
            MDC.setContextMap(contextMap); // 将父线程的 MDC 环境设置进来
            logger.info("the server is processing the request...");
        }
    }
}