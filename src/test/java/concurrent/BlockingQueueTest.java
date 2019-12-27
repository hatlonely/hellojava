package concurrent;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
    @Test
    public void testBlockingQueue() {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
    }
}
