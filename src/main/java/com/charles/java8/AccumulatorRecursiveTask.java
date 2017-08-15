package com.charles.java8;

import java.util.concurrent.RecursiveTask;

/***************************************
 * @author:Alex Wang
 * @Date:2016/11/2 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class AccumulatorRecursiveTask extends RecursiveTask<Integer> {

    private final int start;

    private final int end;

    private final int[] data;

    private final int LIMIT = 3;

    public AccumulatorRecursiveTask(int start, int end, int[] data) {
        this.start = start;
        this.end = end;
        this.data = data;
    }


    @Override
    protected Integer compute() {
        if ((end - start) <= LIMIT) {
            int result = 0;
            for (int i = start; i < end; i++) {
                result += data[i];
            }
            return result;
        }

        int mid = (start + end) / 2;
        AccumulatorRecursiveTask left = new AccumulatorRecursiveTask(start, mid, data);
        AccumulatorRecursiveTask right = new AccumulatorRecursiveTask(mid, end, data);
        left.fork();

        Integer rightResult = right.compute();
        Integer leftResult = left.join();

        return rightResult + leftResult;
    }
}
