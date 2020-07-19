package com.charles.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * http://www.cs.tau.ac.il/~shanir/nir-pubs-web/Papers/CLH.pdf
 * https://blog.csdn.net/aesop_wubo/article/details/7533186
 * https://www.cnblogs.com/shoshana-kong/p/10831502.html
 * https://stackoverflow.com/questions/43628187/why-clh-lock-need-prev-node-in-java
 */
@Slf4j
public class CLHLockExample {
    private static final AtomicInteger counter = new AtomicInteger(1);

    private static class QNode {
        private final int count; // 打印使用，第几个创建的QNode
        volatile boolean locked;

        public QNode() {
            count = counter.getAndIncrement();
        }

        @Override
        public String toString() {
            return "(QNode_" + count + "_locked:" + locked + ")";
        }
    }

    private interface Lock {

        void lock();

        void unlock();
    }

    private static class CLHLock implements Lock {
        // 尾巴，是所有线程共有的一个。所有线程进来后，把自己设置为tail
        private final AtomicReference<QNode> tail;
        // 前驱节点，每个线程独有一个。
        private final ThreadLocal<QNode> myPred;
        // 当前节点，表示自己，每个线程独有一个。
        private final ThreadLocal<QNode> myNode;

        public CLHLock() {
            // 初始状态 tail指向一个新node(head)节点
            this.tail = new AtomicReference<>(new QNode());
            this.myNode = ThreadLocal.withInitial(QNode::new);
            this.myPred = new ThreadLocal<>();
        }

        private void peekNodeInfo(String text) {
            System.out.println(Thread.currentThread().getName()
                    + " " + text + ". " +
                    // "tail=" + tail.get() + ", " +
                    "myNode" + myNode.get() + ", " +
                    "myPred" + myPred.get());

        }

        @Override
        public void lock() {
            // 获取当前线程的代表节点
            QNode node = myNode.get();
            // 将自己的状态设置为true表示获取锁。
            node.locked = true;
            // 将自己放在队列的尾巴，并且返回以前的值。第一次进将获取构造函数中的那个new QNode
            QNode pred = tail.getAndSet(node);
            // 把旧的节点放入前驱节点。
            myPred.set(pred);
            // 在等待前驱节点的locked域变为false，这是一个自旋等待的过程
            // peekNodeInfo("try lock");
            while (pred.locked) {
            }
            peekNodeInfo("acquire lock success");
        }

        @Override
        public void unlock() {
            // unlock. 获取自己的node。把自己的locked设置为false。
            QNode node = myNode.get();
            node.locked = false;
            // 将当前node指向前驱node，这样操作等于把当前node从链表头部删除（并不是被JVM回收），
            // lock方法中再也拿不到当前Node的引用了，当前线程若要在unlock之后再次拿锁需重新排队
            // 但是每个线程自己都维护了两个QNode，一个在释放锁的时候把当前node置为前驱node，
            // 另一个在lock方法的时候重新获取尾node作为前驱node，
            // 如果所有的任务都是由固定数量的线程池执行的话，你会看到所有的QNode的使用会形成一个环形链表（实际不是）
            myNode.set(myPred.get());
            // peekNodeInfo("release lock success");
        }
    }

    private static class KFC {
        private final Lock lock = new CLHLock();
        private int i = 0;

        public void takeout() {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + ": 拿了第" + ++i + "份");
                if (i % 5 == 0) {
                    System.out.println("----------------------------------------------------------------------------------------");
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        final KFC kfc = new KFC();
        Executor executor = Executors.newFixedThreadPool(5);
        for (int i = 1; i <= 35; i++) {
            executor.execute(kfc::takeout);
        }
    }
}