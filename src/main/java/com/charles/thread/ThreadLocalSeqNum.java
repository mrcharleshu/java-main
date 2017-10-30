package com.charles.thread;

/**
 * https://www.zhihu.com/question/23089780
 * http://blog.csdn.net/lufeng20/article/details/24314381
 * ThreadLocal和线程同步机制相比有什么优势呢？ThreadLocal和线程同步机制都是为了解决多线程中相同变量的访问冲突问题。
 * ========
 * 在同步机制中，通过对象的锁机制保证同一时间只有一个线程访问变量。这时该变量是多个线程共享的，
 * 使用同步机制要求程序慎密地分析什么时候对变量进行读写，什么时候需要锁定某个对象，
 * 什么时候释放对象锁等繁杂的问题，程序设计和编写难度相对较大。
 * ========
 * 而ThreadLocal则从另一个角度来解决多线程的并发访问。ThreadLocal会为每一个线程提供一个独立的变量副本，
 * 从而隔离了多个线程对数据的访问冲突。因为每一个线程都拥有自己的变量副本，从而也就没有必要对该变量进行同步了。
 * ThreadLocal提供了线程安全的共享对象，在编写多线程代码时，可以把不安全的变量封装进ThreadLocal。
 * 由于ThreadLocal中可以持有任何类型的对象，低版本JDK所提供的get()返回的是Object对象，需要强制类型转换。
 * 但JDK 5.0通过泛型很好的解决了这个问题，在一定程度地简化ThreadLocal的使用。
 * ========
 * 概括起来说，对于多线程资源共享的问题，同步机制采用了“以时间换空间”的方式，而ThreadLocal采用了“以空间换时间”的方式。
 * 前者仅提供一份变量，让不同的线程排队访问，而后者为每一个线程都提供了一份变量，因此可以同时访问而互不影响。
 * ========
 * ThreadLocal是如何做到为每一个线程维护变量的副本的呢？其实实现的思路很简单：
 * 在ThreadLocal类中有一个Map，用于存储每一个线程的变量副本，Map中元素的键为线程对象，而值对应线程的变量副本
 * ========
 * 当使用ThreadLocal维护变量时，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
 * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本从线程的角度看，
 * 目标变量就象是线程的本地变量，这也是类名中“Local”所要表达的意思
 * ========
 */
public class ThreadLocalSeqNum {
    private static class SeqNumProducer {
        // ThreadLocal的作用是提供线程内的局部变量，这种变量在线程的生命周期内起作用，
        // 减少同一个线程内多个函数或者组件之间一些公共变量的传递的复杂度
        // ①通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值
        private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
            @Override
            protected Integer initialValue() {
                return 0;
            }
        };

        // ②获取下一个序列值
        int getNextNum() {
            seqNum.set(seqNum.get() + 1);
            return seqNum.get();
        }
    }

    private static class SeqNumConsumer extends Thread {
        private SeqNumProducer sn;

        SeqNumConsumer(SeqNumProducer sn) {
            this.sn = sn;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                // ④每个线程打出3个序列值
                print(sn.getNextNum());
            }
        }
    }

    private static void print(int num) {
        System.out.println("[" + Thread.currentThread().getName() + "] --> [sn-" + num + "]");
    }

    public static void main(String[] args) {
        SeqNumProducer sn = new SeqNumProducer();
        print(0);
        System.out.println();
        // ③ 3个线程共享sn，各自产生序列号
        new SeqNumConsumer(sn).start();
        new SeqNumConsumer(sn).start();
        new SeqNumConsumer(sn).start();
        // 每个线程所产生的序号虽然都共享同一个TestNum实例，但它们并没有发生相互干扰的情况，而是各自产生独立的序列号，
        // 这是因为我们通过ThreadLocal为每一个线程提供了单独的副本
    }
}