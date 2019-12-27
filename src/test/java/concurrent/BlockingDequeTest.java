package concurrent;

import org.junit.Test;

import java.util.concurrent.*;

public class BlockingDequeTest {
    @Test
    public void testBlockingDeque() {
        BlockingDeque queue = new LinkedBlockingDeque();
        ExecutorService es = Executors.newCachedThreadPool();

        // 生产者
        for (int i = 0; i < 3; i++) {
            es.execute(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        queue.putFirst(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                    }
                    for (int j = 0; j < 10; j++) {
                        queue.putLast(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                    }
                    for (int j = 0; j < 10; j++) {
                        queue.offerFirst(Math.abs(ThreadLocalRandom.current().nextInt() % 100), 200, TimeUnit.MILLISECONDS);
                    }
                    for (int j = 0; j < 10; j++) {
                        queue.offerLast(Math.abs(ThreadLocalRandom.current().nextInt() % 100), 200, TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // 消费者
        for (int i = 0; i < 3; i++) {
            es.execute(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        System.out.println("take " + queue.takeFirst());
                    }
                    for (int j = 0; j < 10; j++) {
                        System.out.println("take " + queue.takeLast());
                    }
                    for (int j = 0; j < 10; j++) {
                        System.out.println("take " + queue.pollFirst(200, TimeUnit.MILLISECONDS));
                    }
                    for (int j = 0; j < 10; j++) {
                        System.out.println("take " + queue.pollLast(200, TimeUnit.MILLISECONDS));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        try {
            es.shutdown();
            while (!es.awaitTermination(1, TimeUnit.SECONDS)) {
                // nothing to do
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
