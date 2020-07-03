package com.charles.thread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 基于Unsafe实现TreiberStack
 * @author Charles
 */
public class TreiberStack<E> {
    private volatile Node<E> head;

    public void push(E item) {
        Objects.requireNonNull(item);
        Node<E> newHead = new Node<>(item);
        Node<E> oldHead;
        int count = 0;
        do {
            oldHead = head;
            count++;
        } while (!tryPush(oldHead, newHead, count));
        newHead.next = oldHead;
    }

    private boolean tryPush(Node<E> oldHead, Node<E> newHead, int count) {
        boolean isSuccess = UNSAFE.compareAndSwapObject(this, HEAD, oldHead, newHead);
        System.out.println(currentThreadName() + " try push [" + count + "]," +
                " oldHead = " + getValue(oldHead) +
                " newHead = " + getValue(newHead) +
                " isSuccess = " + isSuccess);
        return isSuccess;
    }

    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = head;
            System.out.println(currentThreadName() + " do pop:" +
                    " oldHead = " + getValue(oldHead) +
                    " newHead = " + Optional.ofNullable(head).map(s -> s.next.item).orElse(null));
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.next;
        } while (!trySwap(oldHead, newHead));
        oldHead.next = null;
        return oldHead.item;
    }

    private boolean trySwap(Node<E> oldHead, Node<E> newHead) {
        boolean isSuccess = UNSAFE.compareAndSwapObject(this, HEAD, oldHead, newHead);
        System.out.println(currentThreadName() + " try pop:" +
                " oldHead = " + getValue(oldHead) +
                " currentHead = " + getValue(head) +
                " newHead = " + getValue(newHead) +
                " isSuccess: " + isSuccess);
        return isSuccess;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        Node<E> current = head;
        int size = 0;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    public E peek() {
        Node<E> eNode = head;
        if (eNode == null) {
            return null;
        } else {
            return eNode.item;
        }
    }

    private E getValue(Node<E> n) {
        return Optional.ofNullable(n).map(t -> t.item).orElse(null);
    }

    public String toString() {
        if (head == null) {
            return "Stack is empty";
        } else {
            StringBuilder sb = new StringBuilder();
            Node<E> current = head;
            while (current != null) {
                sb.append(current.item).append(",");
                current = current.next;
            }

            return sb.substring(0, sb.length() - 1);
        }
    }

    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E item) {
            this.item = item;
        }
    }

    // Unsafe mechanics
    private static final Unsafe UNSAFE;
    private static final long HEAD;
    private static final long NEXT;

    static {
        try {
            Field getUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            getUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) getUnsafe.get(null);

            Class<?> k = TreiberStack.class;
            HEAD = UNSAFE.objectFieldOffset(k.getDeclaredField("head"));
            NEXT = UNSAFE.objectFieldOffset(Node.class.getDeclaredField("next"));
        } catch (Exception x) {
            throw new Error(x);
        }
    }

    private static class RandomValue {
        private final Integer value;

        public RandomValue() {
            this.value = new Random().nextInt(Integer.MAX_VALUE);
        }

        public Integer getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    private static String currentThreadName() {
        return System.nanoTime() + " / " + Thread.currentThread().getName();
    }

    public static void main(String[] args) throws InterruptedException {
        TreiberStack<RandomValue> ts = new TreiberStack<>();
        ExecutorService es = Executors.newFixedThreadPool(10);
        Thread.sleep(2000);
        for (int i = 0; i < 5; i++) {
            es.submit(() -> ts.push(new RandomValue()));
        }
        for (int i = 0; i < 50; i++) {
            es.submit((Runnable) ts::pop);
        }
    }
}