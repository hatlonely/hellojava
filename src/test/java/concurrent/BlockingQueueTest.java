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
                        int k = ThreadLocalRandom.current().nextInt() % 100;
                        System.out.println("put " + k);
                        queue.put(k);
                        Thread.sleep(500);
                    }
                    for (int j = 0; j < 10; j++) {
                        int k = ThreadLocalRandom.current().nextInt() % 100;
                        System.out.println("offer " + k);
                        queue.offer(k, 1000, TimeUnit.MILLISECONDS);
                        Thread.sleep(500);
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
                        int k = ThreadLocalRandom.current().nextInt() % 100;
                        System.out.println("put " + k);
                        queue.transfer(k);
                        Thread.sleep(500);
                    }
                    for (int j = 0; j < 10; j++) {
                        int k = ThreadLocalRandom.current().nextInt() % 100;
                        System.out.println("offer " + k);
                        queue.tryTransfer(k, 1000, TimeUnit.MILLISECONDS);
                        Thread.sleep(500);
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
}
