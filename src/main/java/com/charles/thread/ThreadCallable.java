package com.charles.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadCallable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Future<Integer>> list = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            list.add(service.submit(new CallableTask((int) (Math.random() * 100))));
        }
        int sum = 0;
        for (Future<Integer> future : list) {
            // while(!future.isDone()) ;
            sum += future.get();
        }
        service.shutdown();
        while (!service.isTerminated()) {
        }
        System.out.println(sum);
    }


    private static class CallableTask implements Callable<Integer> {
        private int upperBounds;

        public CallableTask(int upperBounds) {
            this.upperBounds = upperBounds;
        }

        @Override
        public Integer call() throws Exception {
            int sum = 0;
            for (int i = 1; i <= upperBounds; i++) {
                sum += i;
            }
            return sum;
        }
    }
}

