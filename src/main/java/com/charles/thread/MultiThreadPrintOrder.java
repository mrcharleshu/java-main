package com.charles.thread;

public class MultiThreadPrintOrder {
    private static final Object OBJECT = new Object();
    private volatile int pos = 1;
    private volatile int count = 0;
    private static final int THREAD_NUM = 10;

    public void print(int i) {
        synchronized (this) {
            if (pos == i) {
                System.out.println("T-" + i + " " + count);
                pos = i % THREAD_NUM + 1;
                count = 0;
            } else {
                count++;
            }
        }
    }

    public static void main(String[] args) {
        MultiThreadPrintOrder object = new MultiThreadPrintOrder();
        for (int i = 1; i <= THREAD_NUM; i++) {
            final int j = i;
            new Thread(() -> object.print(j)).start();
        }
    }
}