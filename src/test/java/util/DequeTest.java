package util;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DequeTest {
    @Test
    public void testDeque() {
        {
            Deque<Integer> deque = new ArrayDeque<>();
            assertThrows(NoSuchElementException.class, deque::getFirst);
            assertThrows(NoSuchElementException.class, deque::getLast);
            assertThrows(NoSuchElementException.class, deque::removeFirst);
            assertThrows(NoSuchElementException.class, deque::removeLast);
            IntStream.range(0, 5).forEach(deque::addFirst);
            IntStream.range(5, 10).forEach(deque::addLast);
            assertThat(deque.toArray(), equalTo(new Integer[]{4, 3, 2, 1, 0, 5, 6, 7, 8, 9}));
            assertEquals(deque.getFirst(), Integer.valueOf(4));
            assertEquals(deque.getLast(), Integer.valueOf(9));
            assertEquals(deque.removeFirst(), Integer.valueOf(4));
            assertEquals(deque.removeLast(), Integer.valueOf(9));
        }
        {
            Deque<Integer> deque = new ArrayDeque<>();
            assertEquals(deque.peekFirst(), null);
            assertEquals(deque.peekLast(), null);
            assertEquals(deque.pollFirst(), null);
            assertEquals(deque.pollLast(), null);
            IntStream.range(0, 5).forEach(deque::offerFirst);
            IntStream.range(5, 10).forEach(deque::offerLast);
            assertThat(deque.toArray(), equalTo(new Integer[]{4, 3, 2, 1, 0, 5, 6, 7, 8, 9}));
            assertEquals(deque.peekFirst(), Integer.valueOf(4));
            assertEquals(deque.peekLast(), Integer.valueOf(9));
            assertEquals(deque.pollFirst(), Integer.valueOf(4));
            assertEquals(deque.pollLast(), Integer.valueOf(9));
        }
        {
            Deque<Integer> deque = new ArrayDeque<>();
            IntStream.range(0, 10).forEach(deque::push);
            assertThat(deque.toArray(), equalTo(new Integer[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0}));
            assertEquals(deque.element(), Integer.valueOf(9));
            assertEquals(deque.pop(), Integer.valueOf(9));
        }
        {
            Deque<Integer> deque = new ArrayDeque<>();
            IntStream.range(0, 10).forEach(deque::push);
            assertTrue(deque.removeFirstOccurrence(2));
            assertTrue(deque.removeLastOccurrence(8));
            assertThat(deque.toArray(), equalTo(new Integer[]{9, 7, 6, 5, 4, 3, 1, 0}));
        }
    }
}
