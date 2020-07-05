package com.charles.thread.state;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1、一个整数在jvm中占用了4个字节，共32bits
 * 2、最高位的bit代表符号位，0为正数、1为负，剩余的31bits则代表数字部分
 * 3、反码加1即为补码
 * 4、对于负数而言，是以补码的形式存储在内存中的。以-7（int）为例
 * ---- 1)、将-7的绝对值转化为二进制：
 * -------- 0000 0000 0000 0000 0000 0000 0000 0111
 * ---- 2)：将上面的二进制以反码表示：
 * -------- 1111 1111 1111 1111 1111 1111 1111 1000
 * ---- 3)：转化为补码：
 * -------- 1111 1111 1111 1111 1111 1111 1111 1001
 * @author Charles
 */
@Slf4j(topic = "ThreadPoolExecutorCtlAnalysis")
public class ThreadPoolExecutorCtlAnalysis {
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;// 000,11111111111111111111111111111

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;  // 111,00000000000000000000000000000
    private static final int SHUTDOWN   = 0 << COUNT_BITS;  // 000,00000000000000000000000000000
    private static final int STOP       = 1 << COUNT_BITS;      // 001,00000000000000000000000000000
    private static final int TIDYING    = 2 << COUNT_BITS;   // 010,00000000000000000000000000000
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

    /**
     * 通过与运算把RunState和WorkerCount的值合并到一处，即最终的ctl的值
     * @param rs RunState运行状态
     * @param wc WorkerCount工作线程数
     * @return int
     */
    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    private static Runnable buildRunnableTask() {
        return () -> {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Task finished.");
        };
    }

    private static int getCtlValue(ThreadPoolExecutor executor, Field field) {
        //noinspection ConstantConditions
        return ((AtomicInteger) ReflectionUtils.getField(field, executor)).get();
    }

    static String formatBinaryString(int state) {
        StringBuilder binaryString = new StringBuilder(Integer.toBinaryString(state));
        if (binaryString.length() < Integer.SIZE) {
            for (int i = binaryString.length(); i < Integer.SIZE; i++) {
                binaryString.insert(0, "0");
            }
        }
        return binaryString.substring(0, 3) + "," + binaryString.substring(3, Integer.SIZE);
    }

    private static void peekThreadPoolExecuteState(ThreadPoolExecutor executor, Field ctlField) {
        log.info("------------------- ThreadPoolExecuteState -------------------");
        int ctlValue = getCtlValue(executor, ctlField);
        log.info("getCtlValue  : {}", formatBinaryString(ctlValue));
        log.info("workerCountOf: {}", workerCountOf(ctlValue));
        log.info("Is    RUNNING: {}", runStateOf(ctlValue) == RUNNING);
        log.info("Is   SHUTDOWN: {}", runStateOf(ctlValue) == SHUTDOWN);
        log.info("Is       STOP: {}", runStateOf(ctlValue) == STOP);
        log.info("Is    TIDYING: {}", runStateOf(ctlValue) == TIDYING);
        log.info("Is TERMINATED: {}", runStateOf(ctlValue) == TERMINATED);
    }

    public static void main(String[] args) throws NoSuchFieldException, InterruptedException {
        // 打印出来看看几种状态的二进制表示
        log.info("{} --> CAPACITY", formatBinaryString(CAPACITY));
        log.info("{} --> RUNNING", formatBinaryString(RUNNING));
        log.info("{} --> STOP", formatBinaryString(STOP));
        log.info("{} --> TERMINATED", formatBinaryString(TERMINATED));
        // 创建一个线程池，运行两个任务
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 2, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1));
        executor.submit(buildRunnableTask());
        executor.submit(buildRunnableTask());
        executor.submit(buildRunnableTask());
        // 休眠一秒钟，可以拿到中间状态的ctl
        Thread.sleep(1000);
        log.info("getActiveCount(): {}", executor.getActiveCount());
        // 通过反射能拿到ThreadPoolExecutor的ctl的值
        Field ctlField = ThreadPoolExecutor.class.getDeclaredField("ctl");
        ctlField.setAccessible(true);
        // 线程池运行中的状态可通过ctl拿到
        peekThreadPoolExecuteState(executor, ctlField);
        // 终止线程池，再来看看线程池中ctl的状态
        executor.shutdownNow();
        peekThreadPoolExecuteState(executor, ctlField);
        // 休眠2秒钟，看看线程池最终的状态
        Thread.sleep(2000);
        peekThreadPoolExecuteState(executor, ctlField);
    }
}
