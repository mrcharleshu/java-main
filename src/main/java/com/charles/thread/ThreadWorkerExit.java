package com.charles.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @see ThreadPoolExecutor#processWorkerExit(java.util.concurrent.ThreadPoolExecutor.Worker, boolean)
 */
public class ThreadWorkerExit {
    private final ExecutorService executor;

    public ThreadWorkerExit() {
        System.out.println("creating service");
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            // Submit方法包裹了异常
            // service.submit(() -> {
            executor.execute(() -> {
                int a = 4, b = 0;
                System.out.println("------------------------------------------------------");
                System.out.println("Thread Name before divide by zero:" + Thread.currentThread().getName());
                System.out.println("a and b=" + a + ":" + b);
                System.out.println("a/b:" + (a / b));
            });
        }
        executor.shutdown();
    }

    public static void main(String[] args) {
        new ThreadWorkerExit().run();
    }
}