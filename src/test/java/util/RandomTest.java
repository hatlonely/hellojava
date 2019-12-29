package util;

import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RandomTest {
    @Test
    public void testThreadLocalRandom() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        System.out.println(r.nextInt());
        System.out.println(r.nextLong());
        System.out.println(r.nextFloat());
        System.out.println(r.nextDouble());
    }

    @Test
    public void testRandom() {
        // java.util.Random是线程安全的。
        // 但是，跨线程的同时使用java.util.Random实例可能会遇到争用，从而导致性能下降。
        // 在多线程设计中考虑使用ThreadLocalRandom
        Random r = new Random();
        System.out.println(r.nextInt());
        System.out.println(r.nextLong());
        System.out.println(r.nextFloat());
        System.out.println(r.nextDouble());
    }

    @Test
    public void testStream() {
        Random r = new Random();
        List<Integer> li = r.ints().limit(10).boxed().map((x) -> Math.abs(x % 100)).collect(Collectors.toList());
        System.out.println(li);
    }

    @Test
    public void testMathRandom() {
        System.out.println(Math.random());
    }
}
