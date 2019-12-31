package util;

import org.junit.Test;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QueueTest {
    @Test
    public void testQueue() {
        {
            Queue<Integer> queue = new LinkedList<>();
            // add / remove / element
            assertThrows(NoSuchElementException.class, queue::remove);
            assertThrows(NoSuchElementException.class, queue::element);
            IntStream.range(0, 10).forEach(queue::add);
            assertThat(queue.toArray(), equalTo(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
            assertEquals(queue.element(), Integer.valueOf(0));
            assertEquals(queue.remove(), Integer.valueOf(0));
        }
        {
            Queue<Integer> queue = new LinkedList<>();
            // offer / poll / peek
            assertEquals(queue.poll(), null);
            assertEquals(queue.peek(), null);
            IntStream.range(0, 10).forEach(queue::offer);
            assertThat(queue.toArray(), equalTo(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
            assertEquals(queue.peek(), Integer.valueOf(0));
            assertEquals(queue.poll(), Integer.valueOf(0));
        }
    }

    @Test
    public void testPriorityQueue() {
        {
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            IntStream.range(0, 5).map(x -> 9 - x).forEach(pq::add);
            IntStream.range(0, 5).forEach(pq::add);
            for (int i = 0; i < 10; i++) {
                assertEquals(pq.element(), Integer.valueOf(i));
                assertEquals(pq.remove(), Integer.valueOf(i));
            }
        }
        {
            PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> (b - a));
            IntStream.range(0, 5).map(x -> 9 - x).forEach(pq::add);
            IntStream.range(0, 5).forEach(pq::add);
            for (int i = 9; i >= 0; i--) {
                assertEquals(pq.element(), Integer.valueOf(i));
                assertEquals(pq.remove(), Integer.valueOf(i));
            }
        }
    }

    @Test
    public void testArrayBlockingQueue() {
        Queue<Integer> queue = new ArrayBlockingQueue<>(10);
        // empty
        assertThrows(NoSuchElementException.class, queue::remove);
        assertEquals(queue.poll(), null);
        assertThrows(NoSuchElementException.class, queue::element);
        assertEquals(queue.peek(), null);
        IntStream.range(0, 10).forEach(queue::add);
        // full
        assertThrows(IllegalStateException.class, () -> queue.add(10));
        assertFalse(queue.offer(10));
    }
}
