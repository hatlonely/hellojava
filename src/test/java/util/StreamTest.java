package util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class StreamTest {
    private static void createFile() throws IOException {
        StreamTest.deleteFile();
        BufferedWriter bw = Files.newBufferedWriter(Paths.get("/tmp/test.txt"));
        bw.write("hello world");
        bw.newLine();
        bw.write("hello world");
        bw.newLine();
        bw.close();
    }

    private static void deleteFile() throws IOException {
        Files.deleteIfExists(Paths.get("/tmp/test.txt"));
    }

    @Before
    public void setUp() throws IOException {
        StreamTest.createFile();
    }

    @After
    public void tearDown() throws IOException {
        StreamTest.deleteFile();
    }

    @Test
    public void testCreateStream() throws IOException {
        // 从容器中创建
        Stream<Integer> stream1 = List.of(1, 2, 3, 4, 5).stream();
        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5);

        // 创建随机数
        Stream<Integer> stream3 = new Random().ints().limit(10).boxed();
        Stream<Integer> stream4 = ThreadLocalRandom.current().ints().limit(10).boxed();

        // 文件流
        Stream<String> stream5 = new BufferedReader(new FileReader("/tmp/test.txt")).lines();

        // IntStream
        Stream<Integer> stream6 = IntStream.range(1, 10).boxed();

        // generate
        Stream<Integer> stream7 = Stream.generate(() -> (int) System.currentTimeMillis()).limit(10);

        // iterate
        Stream<Integer> stream8 = Stream.iterate(1, x -> x + 1).limit(10);
        Stream<Integer> stream9 = Stream.iterate(1, x -> x < 10, x -> x + 1);

        System.out.println(stream8.collect(Collectors.toList()));
    }

    @Test
    public void testTerminal() {
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).forEach(System.out::print);
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).forEachOrdered(System.out::print);
        assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).toArray(), equalTo(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
        assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).collect(Collectors.toList()), equalTo(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)));
        assertFalse(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).anyMatch(x -> x > 10));
        assertTrue(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).allMatch(x -> x < 10));
        assertTrue(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).noneMatch(x -> x > 10));
        assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).count(), 9);
        assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).findFirst().orElse(0), Integer.valueOf(1));
        assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).findAny().orElse(0), Integer.valueOf(1));
        assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).iterator().next(), Integer.valueOf(1));
        assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).max(Comparator.comparingInt(x -> x)).orElse(0), Integer.valueOf(9));
        assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).min(Comparator.comparingInt(x -> x)).orElse(0), Integer.valueOf(1));
        assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).reduce((x, y) -> x + y).orElse(0), Integer.valueOf(45));
    }

    @Test
    public void testIntermediate() {
        assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).map(x -> x * x).collect(Collectors.toList()), equalTo(List.of(
                1, 4, 9, 16, 25, 36, 49, 64, 81
        )));
        assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).filter(x -> x % 2 == 0).collect(Collectors.toList()), equalTo(List.of(
                2, 4, 6, 8
        )));
        assertThat(Stream.of(4, 1, 2, 1, 2, 4, 3, 3).distinct().collect(Collectors.toList()), equalTo(List.of(
                4, 1, 2, 3
        )));
        assertThat(Stream.of(6, 4, 7, 3, 2, 9, 1, 5, 8).sorted().collect(Collectors.toList()), equalTo(List.of(
                1, 2, 3, 4, 5, 6, 7, 8, 9
        )));
        assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).limit(3).collect(Collectors.toList()), equalTo(List.of(
                1, 2, 3
        )));
        assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).skip(6).collect(Collectors.toList()), equalTo(List.of(
                7, 8, 9
        )));
        assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).peek(System.out::println).collect(Collectors.toList()), equalTo(List.of(
                1, 2, 3, 4, 5, 6, 7, 8, 9
        )));
        assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).mapToInt(x -> x * x).sum(), 285);
        assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).takeWhile(x -> x < 5).collect(Collectors.toList()), equalTo(List.of(
                1, 2, 3, 4
        )));
        assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).dropWhile(x -> x < 5).collect(Collectors.toList()), equalTo(List.of(
                5, 6, 7, 8, 9
        )));

        System.out.println(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).sequential().collect(Collectors.toList()));
        System.out.println(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).parallel().collect(Collectors.toList()));
    }

    @Test
    public void testGroupBy() {
        class Student {
            private final String grade;
            private final String name;
            private final int chinese;
            private final int english;

            private Student(String grade, String name, int chinese, int english) {
                this.grade = grade;
                this.name = name;
                this.chinese = chinese;
                this.english = english;
            }

            @Override
            public String toString() {
                return "[" + grade + " " + name + " " + chinese + " " + english + "]";
            }
        }

        Random random = new Random();
        Stream<Student> stream = Stream.generate(() ->
                new Student(
                        "grade" + Math.abs(random.nextInt() % 4 + 1),
                        "student" + Math.abs(random.nextInt() % 1000),
                        Math.abs(random.nextInt() % 30 + 70),
                        Math.abs(random.nextInt() % 30 + 70)
                )
        );

        Map<String, List<Student>> map = stream.limit(10).collect(Collectors.groupingBy(
                x -> x.grade, Collectors.toList()
        ));

        map.forEach((k, v) -> {
            System.out.println(k + " => " + v);
        });
    }
}
