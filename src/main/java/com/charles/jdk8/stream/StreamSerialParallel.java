package com.charles.jdk8.stream;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * http://www.topjavatutorial.com/java-8/java-8-parallel-streams/
 */
public class StreamSerialParallel {
    public static void main(String[] args) {
        primeNumber();
        hyphenSeparator();
        difference();
        hyphenSeparator();
        whenToUse();
    }

    private static void primeNumber() {
        final long primeCount = IntStream.range(1, 100).parallel().filter(number -> isPrime(number)).count();
        System.out.println("Count of prime numbers = " + primeCount);
    }

    private static boolean isPrime(final int number) {
        return number > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(number)).noneMatch(
                divisor -> number % divisor == 0);
    }

    private static void difference() {
        List<String> countries = Lists.newArrayList("Germany", "England", "China", "Denmark", "Brazil");
        // A serial stream processes elements in a serial manner as shown in below example :
        countries.stream().forEach(System.out::println);
        hyphenSeparator();
        // However, with a ParallelStream the data is processed in multiple threads.
        // So, the order of the elements may vary as shown in following example:
        countries.parallelStream().forEach(System.out::println);
        hyphenSeparator();
        // We can process elements in an ordered manner using forEachOrdered() method at the cost of performance.
        countries.parallelStream().forEachOrdered(System.out::println);
        // Although, there is performance cost associated with forEachOrdered(),
        // this can be used with elements that need to be processed in parallel.
        // In such case, stream operations before/after the forEachOrdered() can still use the performance improvements.
    }

    /**
     * Performance improvement with Parallel Stream
     * <p>
     * Parallel stream provides performance improvements depending on the number of CPUs available.
     * <p>
     * For smaller streams, the improvement may be limited because of overhead associated with multitasking.
     * In fact, a stream that processes a small amount of data may actually run slower with parallelStream().
     * <p>
     * Parallel streams are useful on independent stream operations.i.e,
     * result of the operation on one element of the stream is not dependent on result of another element of the stream.
     */
    private static void whenToUse() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            numbers.add(i);
        }
        // Process data sequentially
        long startTime = System.currentTimeMillis();
        numbers.stream().forEach(i -> processElement(i));
        long endTime = System.currentTimeMillis();
        double timeTaken = (endTime - startTime) / 1000;
        System.out.println("Time taken with stream() : " + timeTaken + " milliseconds");

        // Process data in parallel
        startTime = System.currentTimeMillis();
        numbers.parallelStream().forEach(i -> processElement(i));
        endTime = System.currentTimeMillis();
        timeTaken = (endTime - startTime) / 1000;
        System.out.println("Time taken with parallelStream() : " + timeTaken + " milliseconds");
    }

    private static void processElement(int num) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
