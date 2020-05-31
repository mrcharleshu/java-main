package com.charles.thread;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * CompletableFutures可以更多的控制线程池的数量。如果你的任务是io密集型的，你应该使用CompletableFutures；
 * 否则如果你的任务是cpu密集型的，使用比处理器更多的线程是没有意义的，所以选择parallel stream，因为它更容易使用.
 * http://fahdshariff.blogspot.com/2016/06/java-8-completablefuture-vs-parallel.html
 * https://blog.csdn.net/mingliangniwo/article/details/81708468
 *
 * @implNote -Djava.util.concurrent.ForkJoinPool.common.parallelism=8可以影响ForkJoinPool.common()的线程池数量
 * @see java.util.concurrent.ForkJoinPool
 */
public class ParallelStreamVsCompletableFuture {

    public static void main(String[] args) {
        System.out.println("Runtime availableProcessors: " + Runtime.getRuntime().availableProcessors());
        List<SimpleTask> tasks = IntStream.range(0, 20)
                .mapToObj(i -> new SimpleTask(1))
                .collect(Collectors.toList());
        // runParallelStreamVsCompletableFuture(tasks);
        runForkJoinPool(tasks);
    }

    private static void runParallelStreamVsCompletableFuture(List<SimpleTask> tasks) {
        hyphenSeparator("useParallelStream");
        useParallelStream(tasks);
        hyphenSeparator("useCompletableFuture");
        useCompletableFuture(tasks);
        hyphenSeparator("useCompletableFutureWithExecutor");
        useCompletableFutureWithExecutor(tasks);
        hyphenSeparator();
    }

    private static void runForkJoinPool(List<SimpleTask> tasks) {
        hyphenSeparator("useForkJoinPool");
        useForkJoinPool(tasks);
        hyphenSeparator();
    }

    private static class SimpleTask {
        private final int duration;

        public SimpleTask(int duration) {
            this.duration = duration;
        }

        public int calculate() {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(duration * 1000);
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }
            return duration;
        }
    }

    /**
     * 此次并发执行使用了所有的线程 (是ForkJoinPool线程池 plus main 线程).
     *
     * @param tasks
     */
    public static void useParallelStream(List<SimpleTask> tasks) {
        long start = System.nanoTime();
        List<Integer> result = tasks.parallelStream()
                .map(SimpleTask::calculate)
                .collect(Collectors.toList());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("Processed %d tasks in %d millis\n", tasks.size(), duration);
        System.out.println(result);
    }

    /**
     * 以下代码，我们首先获取CompletableFutures集合，然后在每个future上调用join方法去等待他们逐一执行完。
     * 注意，join方法类似于get方法，唯一的不通点是前者不会抛出任何的受检查异常，所以在lambda表达式中更方便一些.
     * <p>
     * 再有，你必须使用两个独立的stream(pipelines)管道，而不是将两个map操作放在一起，
     * 因为stream的中间操作都是懒加载的(intermediate stream operations are lazy)，你最终必须按顺序处理你的任务。
     * 这就是为什么首先需要CompletableFuture在list中，然后允许他们开始执行，直到执行完毕.
     *
     * @param tasks
     * @apiNote 你可以注意到这次仅仅使用了ForkJoinPool线程，不像parallel stream，main线程没有被使用.
     */
    public static void useCompletableFuture(List<SimpleTask> tasks) {
        long start = System.nanoTime();
        List<CompletableFuture<Integer>> futures = tasks.stream()
                .map(t -> CompletableFuture.supplyAsync(t::calculate))
                .collect(Collectors.toList());

        List<Integer> result = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("Processed %d tasks in %d millis\n", tasks.size(), duration);
        System.out.println(result);
    }

    public static void useCompletableFutureWithExecutor(List<SimpleTask> tasks) {
        long start = System.nanoTime();
        ExecutorService executor = Executors.newFixedThreadPool(Math.min(tasks.size(), 20));
        List<CompletableFuture<Integer>> futures = tasks.stream()
                .map(t -> CompletableFuture.supplyAsync(t::calculate, executor))
                .collect(Collectors.toList());

        List<Integer> result = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("Processed %d tasks in %d millis\n", tasks.size(), duration);
        System.out.println(result);
        executor.shutdown();
    }

    public static void useForkJoinPool(List<SimpleTask> tasks) {
        long start = System.nanoTime();
        ForkJoinPool forkJoinPool = new ForkJoinPool(20);
        List<Integer> result = null;
        try {
            Callable<List<Integer>> callable = () ->
                    tasks.parallelStream().map(SimpleTask::calculate).collect(Collectors.toList());
            result = forkJoinPool.submit(callable).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("Processed %d tasks in %d millis\n", tasks.size(), duration);
        System.out.println(result);
    }
}
