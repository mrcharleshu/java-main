package com.charles.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Mutex implements Lock, java.io.Serializable {

    public static void main(String[] args) {
        Mutex mutex = new Mutex();
        // 第一个线程休眠
        Runnable r1 = () -> {
            if (mutex.tryLock()) {
                try {
                    System.out.println(Thread.currentThread().getName() + " sleep");
                    Thread.sleep(30000);
                    mutex.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        // 第二个线程尝试获取锁，超时重来
        Runnable r2 = () -> {
            while (true) {
                try {
                    if (mutex.tryLock(10, TimeUnit.SECONDS)) break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " try lock");
            }
            System.out.println(Thread.currentThread().getName() + " acquire lock");
            mutex.unlock();
        };
        // 阻塞获取锁
        Runnable r3 = () -> {
            mutex.lock();
            System.out.println(Thread.currentThread().getName() + " acquire lock");
            mutex.unlock();
        };
        new Thread(r1).start();
        new Thread(r2).start();
        new Thread(r3).start();
    }

    // 内部类，自定义同步器
    private static class Sync extends AbstractQueuedSynchronizer {
        // 是否处于占用状态
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        // 当状态为0的时候获取锁
        public boolean tryAcquire(int acquires) {
            assert acquires == 1; // Otherwise unused
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        // 释放锁，将状态设置为0
        protected boolean tryRelease(int releases) {
            assert releases == 1; // Otherwise unused
            if (getState() == 0) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        // 返回一个Condition，每个condition都包含了一个condition队列
        Condition newCondition() {
            return new ConditionObject();
        }
    }

    // 仅需要将操作代理到Sync上即可
    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock(long timeout, TimeUnit unit)
            throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }
}