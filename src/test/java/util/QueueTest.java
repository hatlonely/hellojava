package util;

import org.junit.Test;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QueueTest {
    @Test
    void testQueue() {
        {
            Queue<Integer> queue = new LinkedList<>();
            // add / remove / element
            assertThrows(NoSuchElementException.class, queue::remove);
            assertThrows(NoSuchElementException.class, queue::element);
            for (int i = 0; i < 10; i++) {
                queue.add(i);
            }
            for (int i = 0; i < 10; i++) {
                assertEquals(queue.element(), Integer.valueOf(i));
                assertEquals(queue.remove(), Integer.valueOf(i));
            }
        }
        {
            Queue<Integer> queue = new LinkedList<>();
            // offer / poll / peek
            queue.clear();
            assertEquals(queue.poll(), null);
            assertEquals(queue.peek(), null);
            for (int i = 0; i < 10; i++) {
                queue.offer(i);
            }
            for (int i = 0; i < 10; i++) {
                assertEquals(queue.peek(), Integer.valueOf(i));
                assertEquals(queue.poll(), Integer.valueOf(i));
            }
        }
    }

    @Test
    public void testPriorityQueue() {
        {
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            for (int i = 0; i < 5; i++) {
                pq.add(9 - i);
            }
            for (int i = 0; i < 5; i++) {
                pq.add(i);
            }
            for (int i = 0; i < 10; i++) {
                assertEquals(pq.element(), Integer.valueOf(i));
                assertEquals(pq.remove(), Integer.valueOf(i));
            }
        }
        {
            PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> (b - a));
            for (int i = 0; i < 5; i++) {
                pq.add(9 - i);
            }
            for (int i = 0; i < 5; i++) {
                pq.add(i);
            }
            for (int i = 9; i >= 0; i--) {
                assertEquals(pq.element(), Integer.valueOf(i));
                assertEquals(pq.remove(), Integer.valueOf(i));
            }
        }
    }

    @Test
    public void testBlockingQueue() {
        Queue<Integer> queue = new ArrayBlockingQueue<>(10);
        // empty
        assertThrows(NoSuchElementException.class, queue::remove);
        assertEquals(queue.poll(), null);
        assertThrows(NoSuchElementException.class, queue::element);
        assertEquals(queue.peek(), null);

        for (int i = 0; i < 10; i++) {
            queue.add(i);
        }
        // full
        assertThrows(IllegalStateException.class, () -> queue.add(10));
        assertFalse(queue.offer(10));
    }
}
