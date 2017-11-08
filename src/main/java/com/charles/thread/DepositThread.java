package com.charles.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在没有同步的情况下，执行结果通常是显示账户余额在10元以下，
 * 出现这种状况的原因是，当一个线程A试图存入1元的时候，另外一个线程B也能够进入存款的方法中，
 * 线程B读取到的账户余额仍然是线程A存入1元钱之前的账户余额，因此也是在原来的余额0上面做了加1元的操作，
 * 同理线程C也会做类似的事情，所以最后100个线程执行结束时，本来期望账户余额为100元，
 * 但实际得到的通常在10元以下（很可能是1元哦）。
 * 解决这个问题的办法就是同步，当一个线程对银行账户存钱时，需要将此账户锁定，待其操作完成后才允许其他的线程进行操作
 * <p>
 * 下面的例子演示了100个线程同时向一个银行账户中存入1元钱，在没有使用同步机制和使用同步机制情况下的执行情况
 */
public class DepositThread {
    private static final Logger logger = LoggerFactory.getLogger(DepositThread.class);

    public static void main(String[] args) {
        Account account = new Account();
        ExecutorService service = Executors.newFixedThreadPool(100);
        for (int i = 1; i <= 100; i++) {
            service.execute(new DepositTask(account, 1));
        }
        service.shutdown();
        while (!service.isTerminated()) {
        }
        logger.info("账户余额: {}", account.getBalance());
    }

    /**
     * 银行账户
     */
    private static class Account {
        private static final Logger logger = LoggerFactory.getLogger(Account.class);
        private double balance;     // 账户余额

        /**
         * 存款
         * @param money 存入金额
         */
        public synchronized void deposit(double money) {
            double newBalance = balance + money;
            try {
                Thread.sleep(10);   // 模拟此业务需要一段处理时间
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            balance = newBalance;
            logger.debug("Balance={}, deposit={}", balance, money);
        }

        public double getBalance() {
            return balance;
        }
    }

    /**
     * 存钱线程
     */
    private static class DepositTask implements Runnable {
        private static final Logger logger = LoggerFactory.getLogger(DepositTask.class);
        private Account account;    // 存入账户
        private double money;       // 存入金额

        public DepositTask(Account account, double money) {
            this.account = account;
            this.money = money;
        }

        @Override
        public void run() {
            logger.debug("Before Deposit: Balance={}", account.getBalance());
            // synchronized (account) {
            account.deposit(money);
            //}
            logger.debug("After Deposit: Balance={}", account.getBalance());
        }
    }

    /**
     * Java 5通过Lock接口提供了显式的锁机制（explicit lock），增强了灵活性以及对线程的协调。
     * Lock接口中定义了加锁（lock()）和解锁（unlock()）的方法，同时还提供了newCondition()方法来产生用于线程之间通信的Condition对象；
     * 此外，Java 5还提供了信号量机制（semaphore），信号量可以用来限制对某个共享资源进行访问的线程的数量。
     * 在对资源进行访问之前，线程必须得到信号量的许可（调用Semaphore对象的acquire()方法）；
     * 在完成对资源的访问后，线程必须向信号量归还许可（调用Semaphore对象的release()方法）
     */
    private static class LockAccount {
        private static final Logger logger = LoggerFactory.getLogger(LockAccount.class);
        private Lock accountLock = new ReentrantLock();
        private double balance; // 账户余额

        /**
         * 存款
         * @param money 存入金额
         */
        public void deposit(double money) {
            accountLock.lock();
            try {
                double newBalance = balance + money;
                try {
                    Thread.sleep(10); // 模拟此业务需要一段处理时间
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                balance = newBalance;
            } finally {
                accountLock.unlock();
            }
            logger.debug("Balance={}, deposit={}", balance, money);
        }

        public double getBalance() {
            return balance;
        }
    }
}