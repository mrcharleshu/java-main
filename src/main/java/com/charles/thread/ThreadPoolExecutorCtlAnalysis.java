package com.charles.thread;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1、一个整数(都是有符号)在jvm 占用了4个字节，共32bits
 * 2、最高位的bit代表符号位，0:整数、1：负数；剩余的31bits则代表数字部分；
 * 3、对于负数而言，是以补码的形式存储在内存中的。以-7（int）为例
 * 第一步：将-7的绝对值转化为二进制 0000 0111
 * 第二步：将上面的二进制以反码表示 1111 1000
 * 第三步：转化为补码：11111111111111111111111111111001
 * @author Charles
 */
public class ThreadPoolExecutorCtlAnalysis {
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;// 000,11111111111111111111111111111

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;  // 111,00000000000000000000000000000
    private static final int SHUTDOWN = 0 << COUNT_BITS;  // 000,00000000000000000000000000000
    private static final int STOP = 1 << COUNT_BITS;      // 001,00000000000000000000000000000
    private static final int TIDYING = 2 << COUNT_BITS;   // 010,00000000000000000000000000000
    private static final int TERMINATED = 3 << COUNT_BITS;// 011,00000000000000000000000000000

    // Packing and unpacking ctl

    // RUNNING(3'thread) 111,00000000000000000000000000011
    // ~CAPACITY         111,00000000000000000000000000000
    // RESULT            111,00000000000000000000000000000
    // 与操作取高位获取的就是ctl中保存的的线程池的状态
    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    // RUNNING(3'thread) 111,00000000000000000000000000011
    // CAPACITY          000,11111111111111111111111111111
    // RESULT            000,00000000000000000000000000011
    // 与操作取低位获取的就是ctl中保存的worker数量
    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    private static Runnable buildRunnableTask() {
        return () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task finished.");
        };
    }

    private static int getCtlValue(ThreadPoolExecutor executor, Field ctlField) {
        //noinspection ConstantConditions
        return ((AtomicInteger) ReflectionUtils.getField(ctlField, executor)).get();
    }

    public static void main(String[] args) throws NoSuchFieldException, InterruptedException {
        System.out.println(Integer.toBinaryString(CAPACITY));
        System.out.println(Integer.toBinaryString(STOP));
        System.out.println(Integer.toBinaryString(RUNNING));
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        executor.submit(buildRunnableTask());
        executor.submit(buildRunnableTask());
        Thread.sleep(1000);
        executor.getActiveCount();
        Field ctlField = ThreadPoolExecutor.class.getDeclaredField("ctl");
        ctlField.setAccessible(true);
        System.out.println("WorkerCountOf=" + workerCountOf(getCtlValue(executor, ctlField)));
        System.out.println("IsRunning=" + (runStateOf(getCtlValue(executor, ctlField)) == RUNNING));
        System.out.println("IsStop=" + (runStateOf(getCtlValue(executor, ctlField)) == STOP));
        System.out.println("After 2 seconds.....");
        Thread.sleep(2000);
        executor.shutdownNow();
        System.out.println("WorkerCountOf=" + workerCountOf(getCtlValue(executor, ctlField)));
        System.out.println("IsRunning=" + (runStateOf(getCtlValue(executor, ctlField)) == RUNNING));
        System.out.println("IsStop=" + (runStateOf(getCtlValue(executor, ctlField)) == STOP));
    }
}
