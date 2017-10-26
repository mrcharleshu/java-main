package com.charles.jdk8;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Spliterator是Java 8引入的新接口，顾名思义，Spliterator可以理解为Iterator的Split版本（但用途要丰富很多）。
 * 使用Iterator的时候，我们可以顺序地遍历容器中的元素，使用Spliterator的时候，
 * 我们可以将元素分割成多份，分别交于不于的线程去遍历，以提高效率。
 * 使用 Spliterator 每次可以处理某个元素集合中的一个元素 — 不是从 Spliterator 中获取元素，
 * 而是使用 tryAdvance() 或 forEachRemaining() 方法对元素应用操作。
 * 但Spliterator 还可以用于估计其中保存的元素数量，而且还可以像细胞分裂一样变为一分为二。
 * 这些新增加的能力让流并行处理代码可以很方便地将工作分布到多个可用线程上完成。
 */
public class SumSpliterator implements Spliterator<Long> {
    private final long[] numbers;
    private int currentPosition, endPosition;

    public SumSpliterator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    public SumSpliterator(long[] numbers, int startPosition, int endPosition) {
        //System.out.println(String.format("start=%s,end=%s", startPosition, endPosition));
        this.numbers = numbers;
        this.currentPosition = startPosition;
        this.endPosition = endPosition;
    }

    /**
     * 单个对元素执行给定的动作，如果有剩下元素未处理返回true，否则返回false
     * @param action
     * @return
     */
    @Override
    public boolean tryAdvance(Consumer<? super Long> action) {
        if (currentPosition < endPosition) {
            action.accept(numbers[currentPosition++]);
            return true;
        }
        return false;
    }

    /**
     * 对每个剩余元素执行给定的动作，依次处理，直到所有元素已被处理或被异常终止。默认方法调用tryAdvance方法
     * @param action
     */
    @Override
    public void forEachRemaining(final Consumer<? super Long> action) {
        int pos = currentPosition, end = endPosition;
        currentPosition = end;
        for (; pos < end; pos++) {
            action.accept(numbers[pos]);
        }
    }

    /**
     * 对任务分割，返回一个新的Spliterator迭代器
     * @return
     */
    @Override
    public Spliterator<Long> trySplit() {
        if (estimateSize() <= 1000) {
            return null;
        }
        int middle = (endPosition + currentPosition) >>> 1;
        SumSpliterator prefix = new SumSpliterator(numbers, currentPosition, middle);
        currentPosition = middle;
        return prefix;

        //int currentSize = numbers.length - currentPosition;

        //if (currentSize <= 1_000) {
        //    return null;
        //} else {
        //    currentPosition = currentPosition + 1_000;
        //    return new SumSpliterator(Arrays.copyOfRange(numbers, 1_000, endPosition));
        //}
    }

    /**
     * 用于估算还剩下多少个元素需要遍历
     * @return
     */
    @Override
    public long estimateSize() {
        return endPosition - currentPosition;
    }

    /**
     * 返回当前对象有哪些特征值
     * 特征值其实就是为表示该Spliterator有哪些特性，用于可以更好控制和优化Spliterator的使用
     * @return
     */
    @Override
    public int characteristics() {
        return ORDERED | NONNULL | SIZED | SUBSIZED;
    }

    //是否具有当前特征值
    //default boolean hasCharacteristics(int characteristics) {
    //    return (characteristics() & characteristics) == characteristics;
    //}

    //如果Spliterator的list是通过Comparator排序的，则返回Comparator
    //如果Spliterator的list是自然排序的 ，则返回null
    //其他情况下抛错
    //default Comparator<? super T> getComparator() {
    //    throw new IllegalStateException();
    //}

    public static void main(String[] args) {
        long startMillis = System.currentTimeMillis();
        long[] twoThousandNumbers = LongStream.rangeClosed(1, 100_000_000).toArray();

        Spliterator<Long> spliterator = new SumSpliterator(twoThousandNumbers);
        Stream<Long> stream = StreamSupport.stream(spliterator, true);
        System.out.println(sumValues(stream));
        System.out.println(String.format("Method cost millis=%s", System.currentTimeMillis() - startMillis));
    }

    private static long sumValues(Stream<Long> stream) {
        return stream.reduce(Long::sum).orElse(0L);
    }
}