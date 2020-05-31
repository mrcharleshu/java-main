package com.charles.thread.objectpool;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.util.concurrent.atomic.AtomicInteger;

public class ObjectPoolDemo {
    private static final PooledObjectFactory<Object> factory = new SmPooledObjectFactory<>();
    private static final ObjectPool<Object> pool = new GenericObjectPool<>(factory);
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static class PoolThread extends Thread {
        public void run() {
            Object obj;
            try {
                for (int i = 0; i < 100; i++) {
                    System.out.println("==" + i + "==");
                    obj = pool.borrowObject();
                    System.out.println("Object[" + obj + "] is get");
                    pool.returnObject(obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //以原子性操作自增一
                counter.getAndIncrement();
            }
        }
    }

    public static void main(String[] args) {
        new PoolThread().start();
        new PoolThread().start();
        new PoolThread().start();

        while (true) {
            if (counter.get() == 3) {//等待三个线程全部结束
                pool.close();
                break;
            }
        }
    }
}