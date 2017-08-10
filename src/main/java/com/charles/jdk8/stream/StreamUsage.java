package com.charles.jdk8.stream;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.charles.utils.LineSeparators.hyphenSeparator;
import static java.util.stream.Collectors.*;

/**
 * 流底层核心其实是Spliterator接口的一个实现，而这个Spliterator接口其实本身就是Fork/Join并行框架的一个实现，
 * 所以归根结底要明白流的工作方式，就要明白一下Fork/Join框架的基本思想，
 * 即：以递归的方式将可以并行的任务拆分成更小的子任务，然后将每个子任务的结果合并起来生成整体的最后结果
 */
public class StreamUsage {

    public static void main(String[] args) {
        // forEach();
        // streamCommonUsage();
        // streamWithClasses();
        // countWordsByFlatMap();
        // simpleReduce();
        reduceCombiner();
        // readFileContent();
    }

    private static void forEach() {
        Arrays.asList("a", "b", "d").forEach(e -> System.out.println(e));
        Arrays.asList("a", "b", "d").forEach((String e) -> System.out.println(e));
        Arrays.asList("a", "b", "d").forEach(System.out::println);
        hyphenSeparator();
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }
        map.forEach((key, val) -> System.out.println("Key=" + key + " Val=" + val));
    }

    private static void streamCommonUsage() {
        Student student1 = new Student("Charles", 23, 'M');
        Student student2 = new Student("Lucy", 18, 'F');
        Student student3 = new Student("John", 34, 'M');
        Student student4 = new Student("Amy", 54, 'F');
        List<Student> students = Lists.newArrayList(student1, student2, student3, student4);
        hyphenSeparator("streamCommonUsage map1");
        Map<String, Student> map1 = students.stream().collect(toMap(Student::getName, Function.identity()));
        System.out.println(map1);
        hyphenSeparator("streamCommonUsage map2");
        Map<Character, List<Student>> map2 = students.stream().collect(groupingBy(Student::getGender));
        System.out.println(map2);
        hyphenSeparator("streamCommonUsage map3");
        Map<Character, List<String>> map3 = students.stream().collect(groupingBy(Student::getGender,
                mapping(Student::getName, toList())));
        System.out.println(map3);
        hyphenSeparator("streamCommonUsage map4");
        Map<Character, Long> map4 = students.stream().collect(groupingBy(Student::getGender, counting()));
        System.out.println(map4);
        hyphenSeparator("streamCommonUsage map1 entrySet filter");
        List<String> nameList = map1.entrySet().stream().filter(entry -> entry.getValue().getAge() > 20).map(
                entry -> entry.getValue().getName()).collect(toList());
        System.out.println(nameList);
        hyphenSeparator("streamCommonUsage map1 entrySet filter map stream-join");
        String names = nameList.stream().map(name -> "[" + name + "]").collect(joining(","));
        System.out.println(names);
        hyphenSeparator("Collection.parallelStream & Stream.parallel");
        List<String> list = Lists.newArrayList("ddd2", "aaa2", "bbb1", "aaa1", "bbb3", "ccc", "bbb2", "ddd1");
        Predicate<String> predicate = s -> s.startsWith("a");
        list.parallelStream().sorted().filter(predicate).map(String::toUpperCase).forEach(System.out::println);
        list.stream().parallel().sorted().filter(predicate).forEach(System.out::println);
    }

    private static void streamWithClasses() {
        final Collection<Task> tasks = Arrays.asList(
                new Task(Task.Status.OPEN, 5),
                new Task(Task.Status.OPEN, 13),
                new Task(Task.Status.CLOSED, 8)
        );
        hyphenSeparator("mapToInt");
        // Calculate total points of all active tasks using sum()
        Predicate<Task> isOpen = task -> task.getStatus() == Task.Status.OPEN;
        final int totalPointsOfOpenTasks = tasks.stream().filter(isOpen).mapToInt(Task::getPoints).sum();
        System.out.println("Total points (open task): " + totalPointsOfOpenTasks);
        hyphenSeparator("map and reduce");
        // Calculate total points of all tasks
        final int totalPoints = tasks.stream().parallel().map(Task::getPoints).reduce(0, Integer::sum);
        System.out.println("Total points (all tasks): " + totalPoints);
        hyphenSeparator("groupingBy");
        // Group tasks by their status
        final Map<Task.Status, List<Task>> map = tasks.stream().collect(groupingBy(Task::getStatus));
        System.out.println(map);
        hyphenSeparator("Calculate task point ratio");
        // Calculate the weight of each tasks (as percent of total points)
        final Collection<String> ratio = tasks.stream()   // Stream<String>
                .mapToInt(Task::getPoints)                     // IntStream
                .asLongStream()                                  // LongStream
                .mapToDouble(points -> points / (double) totalPoints)   // DoubleStream
                .boxed()                                         // Stream<Double>
                .mapToLong(weight -> (long) (weight * 100)) // LongStream
                .mapToObj(percentage -> percentage + "%")      // Stream<String>
                .collect(toList());                 // List<String>
        System.out.println("task point ratio: " + ratio);
        hyphenSeparator("flatMapToInt");
        int sum = tasks.stream().flatMapToInt(task -> IntStream.of(task.getStatusValue())).peek(System.out::print).sum();
        System.out.println(" Sum=" + sum);
        hyphenSeparator("flatMap");
        // 查找Article中所有不同的标签
        Article article1 = new Article("JDK8", "Charles", "Collection", "Map");
        Article article2 = new Article("JS", "Colin", "Arrow Function", "Destructing");
        Set<String> tags = Stream.of(article1, article2).flatMap(article -> article.getTags().stream()).collect(toSet());
        System.out.println(tags);
    }

    private static void countWordsByFlatMap() {
        String poetry = "Where, before me, are the ages that have gone?/n"
                + "And where, behind me, are the coming generations?/n"
                + "I think of heaven and earth, without limit, without end,/n"
                + "And I am all alone and my tears fall down.";
        Stream<String> lines = Arrays.stream(poetry.split("/n"));
        Stream<String> words = lines.flatMap(line -> Arrays.stream(line.split(" ")));
        List<String> wordsList = words.map(w -> {
            if (w.endsWith(",") || w.endsWith(".") || w.endsWith("?")) {
                return w.substring(0, w.length() - 1).trim().toLowerCase();
            } else {
                return w.trim().toLowerCase();
            }
        }).distinct().peek(System.out::println).sorted().collect(toList());
        // [ages, all, alone, am, and, are, before, behind, coming, down, earth, end, fall, generations,
        // gone, have, heaven, i, limit, me, my, of, tears, that, the, think, where, without]
        System.out.println(wordsList);
    }

    /**
     * Stream的reduce方法，翻译过来是聚合或者是汇聚成一个的意思，由于Stream本身就代表着一堆数据，
     * 那stream.reduce()方法顾名思义就是把一堆数据聚合成一个数据
     */
    public static void simpleReduce() {
        List<String> strList = Lists.newArrayList("ddd2", "aaa2", "bbb1", "aaa1", "bbb3", "ccc", "bbb2", "ddd1");
        BinaryOperator<String> binaryOperator = (s1, s2) -> {
            System.out.println("S1=" + s1 + " S2=" + s2);
            return s1 + "#" + s2;
        };
        Optional<String> reduced1 = Optional.ofNullable(strList.stream().sorted().reduce("raw", binaryOperator));
        hyphenSeparator();
        String reduced2 = strList.stream().reduce("raw", binaryOperator);
        hyphenSeparator();
        System.out.print("reduced1 = ");
        reduced1.ifPresent(System.out::println);
        System.out.println("reduced2 = " + reduced2);
        List<Integer> intList = Lists.newArrayList(1, 2, 3, 4);
        hyphenSeparator("Integer reduce 1");
        System.out.println(intList.stream().reduce((result, element) -> result + element));
        hyphenSeparator("Integer reduce 2");
        System.out.println(intList.stream().reduce(0, (result, element) -> result + element));
    }


    public static void reduceCombiner() {
        hyphenSeparator("Integer reduce with combiner 1");
        List<Integer> intList = Arrays.asList(1, 2, 3, 4);
        int result1 = intList.stream().parallel().reduce(0, (result, element) -> {
            System.out.println("result=" + result + " element=" + element);
            return result + element;
        }, (u, t) -> {
            System.out.println("u=" + u + " t=" + t);
            return u + t;
        });
        System.out.println(result1);
        hyphenSeparator("Integer reduce with combiner 2");
        StringBuilder result2 = intList.stream().reduce(
                new StringBuilder(), (result, element) -> {
                    System.out.println("result=" + result + " element=" + element);
                    return result.append(element);
                }, (u, t) -> {
                    System.out.println("u=" + u + " t=" + t);
                    return u.append(t);
                }); //这个地方 返回 u或者t也是可以的 运行没错
        System.out.println(result2);
        hyphenSeparator("String reduce with combiner 3");
        List<String> strList = Arrays.asList("abc", "mno", "xyz");
        // 定义相同的accumulator
        BiFunction<Integer, String, Integer> accumulator = (total, str) -> {
            System.out.println(String.format("accumulator: total[%s] s[%s] s.codePointAt(0)[%s]",
                    total, str, str.codePointAt(0)));
            return total + str.codePointAt(0);
        };
        BinaryOperator<Integer> combiner = (a, b) -> {
            System.out.println("combiner: a[" + a + "] b[" + b + "]");
            return 1000000;
        };
        // 如果你使用了parallelStream reduce操作是并发进行的 为了避免竞争 每个reduce线程都会有独立的result
        // combiner的作用在于合并每个线程的result得到最终结果
        int result3 = strList.stream().reduce(0, accumulator, combiner);
        System.out.println(result3);
        hyphenSeparator("String reduce with combiner 4");
        int result4 = strList.parallelStream().reduce(0, accumulator, combiner);
        System.out.println(result4);
        hyphenSeparator("String reduce with combiner 5");
        int result5 = strList.parallelStream().reduce(0, accumulator, (a, b) -> {
                    System.out.println("combiner: a[" + a + "] b[" + b + "] a+b[" + (a + b) + "]");
                    return a + b;
                }
        );
        System.out.println(result5);
    }

    private static void readFileContent() {
        final String filename = "/Users/Charles/Downloads/temp.txt";
        final Path path = new File(filename).toPath();
        try {
            try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
                lines.onClose(() -> System.out.println("Done!")).forEach(System.out::println);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}