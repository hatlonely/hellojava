package concurrent;

import org.junit.Test;

import java.util.concurrent.*;

public class BlockingQueueTest {
    @Test
    public void testBlockingQueue() {
        BlockingQueue<Integer> q1 = new ArrayBlockingQueue<>(10);
        BlockingQueue<Integer> q2 = new LinkedBlockingQueue<>();
        BlockingQueue<Integer> q3 = new LinkedTransferQueue<>();
        BlockingQueue<Integer> q4 = new SynchronousQueue<>();
        BlockingQueue<Integer> q5 = new PriorityBlockingQueue<>();
    }

    @Test
    public void testOfferPollPutTake() {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(100);
        ExecutorService es = Executors.newCachedThreadPool();

        // 生产者
        for (int i = 0; i < 3; i++) {
            es.execute(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        queue.put(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                    }
                    for (int j = 0; j < 10; j++) {
                        queue.offer(Math.abs(ThreadLocalRandom.current().nextInt() % 100), 1000, TimeUnit.MILLISECONDS);
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
                        System.out.println("take " + queue.take());
                    }
                    for (int j = 0; j < 10; j++) {
                        System.out.println("poll " + queue.poll(1000, TimeUnit.MILLISECONDS));
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

    @Test
    public void testTransfer() {
        TransferQueue<Integer> queue = new LinkedTransferQueue<>();
        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < 3; i++) {
            es.execute(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        queue.transfer(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                    }
                    for (int j = 0; j < 10; j++) {
                        queue.tryTransfer(Math.abs(ThreadLocalRandom.current().nextInt() % 100), 1000, TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        for (int i = 0; i < 3; i++) {
            es.execute(() -> {
                try {
                    for (int j = 0; j < 20; j++) {
                        System.out.println("poll " + queue.take());
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

    @Test
    public void testConcurrentQueue() {
        // 这个 queue 只是一个线程安全的队列，但是并没有提供阻塞接口
        // 作为生产者消费者队列时，需要自己处理队列为空时候的阻塞，事实上，并不建议用作生产者消费者队列
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < 3; i++) {
            es.execute(() -> {
                for (int j = 0; j < 20; j++) {
                    queue.offer(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                }
            });

            es.execute(() -> {
                for (int j = 0; j < 20; j++) {
                    System.out.println("poll " + queue.poll());
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
