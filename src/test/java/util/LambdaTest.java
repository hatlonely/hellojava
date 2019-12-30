package util;

import org.junit.Test;

import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;

public class LambdaTest {
    @Test
    public void testFunctionalInterface() {
        Predicate<Integer> greater10 = x -> x > 10;
        Consumer<Integer> print = x -> System.out.println(x);
        Function<Integer, String> intToString = x -> Integer.valueOf(x).toString();
        Supplier<Integer> randInt = () -> ThreadLocalRandom.current().nextInt();
        Comparator<Integer> less = (x, y) -> y - x;
        UnaryOperator<Integer> power = x -> x * x;
        BinaryOperator<Integer> add = (x, y) -> x + y;
        BiConsumer<Integer, String> printis = (i, s) -> System.out.println(i + s);
        Runnable run = () -> System.out.println("hello world");
    }

    @Test
    public void testMethodReference() {
        Consumer<Integer> print = System.out::println;
        Predicate<String> empty = String::isEmpty;
        Supplier<Date> date = Date::new;
    }
}
