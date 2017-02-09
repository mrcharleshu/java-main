package com.charles.jdk8_test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {

    public static void main(String[] args) {
        testCollectors();
    }

    public static void listTest() {
        Arrays.asList("a", "b", "d").forEach(e -> System.out.println(e));
        Arrays.asList("a", "b", "d").forEach((String e) -> System.out.println(e));
        Arrays.asList("a", "b", "d").forEach(System.out::println);
        // Collection.parallelStream & Stream.parallel
        List<String> list = Lists.newArrayList("ddd2", "aaa2", "bbb1", "aaa1", "bbb3", "ccc", "bbb2", "ddd1");
        list.parallelStream().sorted().filter(s -> s.startsWith("a")).map(s -> s.toLowerCase());
        list.stream().parallel().filter((s) -> s.startsWith("a")).forEach(System.out::println);

    }

    public static void mapTest() {
        Map<Integer, String> map = Maps.newHashMap();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }
        map.forEach((key, val) -> System.out.println("Key=" + key + " Val=" + val));
    }

    public static void reduceTest() {
        List<String> list1 = Lists.newArrayList("ddd2", "aaa2", "bbb1", "aaa1", "bbb3", "ccc", "bbb2", "ddd1");
        Optional<String> reduced1 = Optional.ofNullable(list1.stream().sorted().reduce("raw", (s1, s2) -> {
            System.out.println("S1=" + s1 + " S2=" + s2);
            return s1 + "#" + s2;
        }));
        String reduced2 = list1.stream().reduce("raw", (s1, s2) -> {
            System.out.println("S1=" + s1 + " S2=" + s2);
            return s1 + "#" + s2;
        });
        System.out.print("reduced1 = ");
        reduced1.ifPresent(System.out::println);
        System.out.println("reduced2 = " + reduced2);

        List<Integer> list2 = Lists.newArrayList(1, 2, 3, 4);
        System.out.println(list2.stream().reduce((result, element) -> result + element));
        System.out.println(list2.stream().reduce(0, (result, element) -> result + element));
        System.out.println(list2.stream().parallel().reduce(0, (result, element) -> result + element, (u, t) -> {
            System.out.println("u=" + u + " t=" + t);
            return u + t;
        }));

        System.out.println(list2.stream().reduce(
            new StringBuilder(), (result, element) -> result = result.append(element),
            (u, t) -> u = u.append(t))); //这个地方 返回 u或者t也是可以的 运行没错
    }

    public static void streamWithClasses() {
        final Collection<Task> tasks = Arrays.asList(
            new Task(Task.Status.OPEN, 5),
            new Task(Task.Status.OPEN, 13),
            new Task(Task.Status.CLOSED, 8)
        );
        // Calculate total points of all active tasks using sum()
        final long totalPointsOfOpenTasks = tasks.stream().filter(
            task -> task.getStatus() == Task.Status.OPEN).mapToInt(Task::getPoints).sum();
        System.out.println("Total points: " + totalPointsOfOpenTasks);

        // Calculate total points of all tasks
        final double totalPoints = tasks.stream().parallel().map(task -> task.getPoints()).reduce(0, Integer::sum);

        System.out.println("Total points (all tasks): " + totalPoints);
        // Group tasks by their status
        final Map<Task.Status, List<Task>> map = tasks.stream().collect(Collectors.groupingBy(Task::getStatus));
        System.out.println(map);
        // Calculate the weight of each tasks (as percent of total points)
        final Collection<String> result = tasks.stream()   // Stream<String>
            .mapToInt(Task::getPoints)                     // IntStream
            .asLongStream()                                  // LongStream
            .mapToDouble(points -> points / totalPoints)   // DoubleStream
            .boxed()                                         // Stream<Double>
            .mapToLong(weigth -> (long) (weigth * 100)) // LongStream
            .mapToObj(percentage -> percentage + "%")      // Stream<String>
            .collect(Collectors.toList());                 // List<String>

        System.out.println(result);
        System.out.print("flatMapToInt: ");
        int sum = tasks.stream().flatMapToInt(task -> IntStream.of(task.getStatus().getValue())).peek(
            v -> System.out.print(v + "-")).sum();
        System.out.println(" Sum=" + sum);
        System.out.print("flatMap");
        // 查找Article中所有不同的标签
        Article article1 = new Article("JDK8", "Charles", Lists.newArrayList("Collection", "Map"));
        Article article2 = new Article("JS", "Colin", Lists.newArrayList("Arrow Function", "Destructing"));
        List<Article> articles = Lists.newArrayList(article1, article2);
        Set<String> tags = articles.stream().flatMap(article -> article.getTags().stream()).collect(Collectors.toSet());
        System.out.println(tags);
    }

    public static void flatMapTest() {
        String poetry = "Where, before me, are the ages that have gone?/n"
            + "And where, behind me, are the coming generations?/n"
            + "I think of heaven and earth, without limit, without end,/n"
            + "And I am all alone and my tears fall down.";
        Stream<String> lines = Arrays.stream(poetry.split("/n"));
        Stream<String> words = lines.flatMap(line -> Arrays.stream(line.split(" ")));
        List<String> l = words.map(w -> {
            if (w.endsWith(",") || w.endsWith(".") || w.endsWith("?")) {
                return w.substring(0, w.length() - 1).trim().toLowerCase();
            } else {
                return w.trim().toLowerCase();
            }
        }).distinct().peek(w -> System.out.print(w + "-")).sorted().collect(Collectors.toList());
        // [ages, all, alone, am, and, are, before, behind, coming, down, earth, end, fall, generations,
        // gone, have, heaven, i, limit, me, my, of, tears, that, the, think, where, without]
        System.out.println(l);
    }

    public static void reduceThirdArgumentTest() {
        String[] strArray = {"abc", "mno", "xyz"};
        List<String> strList = Arrays.asList(strArray);

        System.out.println("stream test");
        int streamResult = strList.stream().reduce(
            0,
            (total, s) -> {
                System.out.println("accumulator: total[" + total + "] s[" + s + "] s.codePointAt(0)[" + s.codePointAt(0) + "]");
                return total + s.codePointAt(0);
            },
            (a, b) -> {
                System.out.println("combiner: a[" + a + "] b[" + b + "]");
                return 1000000;
            }
        );
        System.out.println("streamResult: " + streamResult);

        System.out.println("parallelStream test");
        int parallelStreamResult = strList.parallelStream().reduce(
            0,
            (total, s) -> {
                System.out.println("accumulator: total[" + total + "] s[" + s + "] s.codePointAt(0)[" + s.codePointAt(0) + "]");
                return total + s.codePointAt(0);
            },
            (a, b) -> {
                System.out.println("combiner: a[" + a + "] b[" + b + "]");
                return 1000000;
            }
        );
        System.out.println("parallelStreamResult: " + parallelStreamResult);

        System.out.println("parallelStream test2");
        int parallelStreamResult2 = strList.parallelStream().reduce(
            0,
            (total, s) -> {
                System.out.println("accumulator: total[" + total + "] s[" + s + "] s.codePointAt(0)[" + s.codePointAt(0) + "]");
                return total + s.codePointAt(0);
            },
            (a, b) -> {
                System.out.println("combiner: a[" + a + "] b[" + b + "] a+b[" + (a + b) + "]");
                return a + b;
            }
        );
        System.out.println("parallelStreamResult2: " + parallelStreamResult2);
    }

    public static void readFileContentWithStream() {
        String filename = "";
        final Path path = new File(filename).toPath();
        try {
            try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
                lines.onClose(() -> System.out.println("Done!")).forEach(System.out::println);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testCollectors() {
        Student student1 = new Student("Charles", 23, "M");
        Student student2 = new Student("Lucy", 18, "F");
        Student student3 = new Student("John", 34, "M");
        Student student4 = new Student("Amy", 54, "F");
        List<Student> students = Lists.newArrayList(student1, student2, student3, student4);
        Map<String, Student> map1 = students.stream().collect(Collectors.toMap(Student::getName, student -> student));
        System.out.println(map1);
        Map<String, Student> map2 = students.stream().collect(Collectors.toMap(Student::getName, Function.identity()));
        System.out.println(map2);
    }

}

class Student {

    private String name;
    private Integer age;
    private String gender;

    public Student() {
    }

    public Student(String name, Integer age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

class Task {

    public enum Status {
        OPEN(1, 2, 3), CLOSED(4, 5, 6);
        private final int[] value;

        Status(int... value) {
            this.value = value;
        }

        public int[] getValue() {
            return value;
        }
    }

    private final Status status;
    private final Integer points;

    Task(final Status status, final Integer points) {
        this.status = status;
        this.points = points;
    }

    public Integer getPoints() {
        return points;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("[%s, %d]", status, points);
    }
}

class Article {

    private final String title;
    private final String author;
    private final List<String> tags;

    public Article(String title, String author, List<String> tags) {
        this.title = title;
        this.author = author;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getTags() {
        return tags;
    }
}