package concurrent;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.*;

public class ConcurrentTest {
    @Test
    public void testConcurrentHashMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        ExecutorService es = Executors.newCachedThreadPool();

        long endTime = System.currentTimeMillis() + 100;
        for (int i = 0; i < 5; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    for (int j = 0; j < 100; j++) {
                        map.put("key" + j, "val" + j);
                    }
                }
            });
        }

        for (int i = 0; i < 5; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    for (int j = 0; j < 100; j++) {
                        System.out.println(map.get("key" + j));
                    }
                }
            });
        }

        for (int i = 0; i < 2; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    for (int j = 0; j < 10; j++) {
                        map.remove("key" + Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                    }
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
    public void testConcurrentSkipListdMap() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        ExecutorService es = Executors.newCachedThreadPool();

        long endTime = System.currentTimeMillis() + 100;
        for (int i = 0; i < 5; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    for (int j = 0; j < 100; j++) {
                        map.put("key" + j, "val" + j);
                    }
                }
            });
        }

        for (int i = 0; i < 5; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        System.out.println(entry.getKey() + " => " + entry.getValue());
                    }
                }
            });
        }

        for (int i = 0; i < 2; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    for (int j = 0; j < 10; j++) {
                        map.remove("key" + Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                    }
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
    public void testConcurrentSkipListSet() {
        ConcurrentSkipListSet<String> set = new ConcurrentSkipListSet<>();
        ExecutorService es = Executors.newCachedThreadPool();

        long endTime = System.currentTimeMillis() + 100;
        for (int i = 0; i < 5; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    for (int j = 0; j < 100; j++) {
                        set.add("key" + j);
                    }
                }
            });
        }

        for (int i = 0; i < 5; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    for (int j = 0; j < 100; j++) {
                        set.contains("key" + j);
                    }
                }
            });
        }

        for (int i = 0; i < 2; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    set.remove("key" + Math.abs(ThreadLocalRandom.current().nextInt() % 100));
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

    @Test
    public void testConcurrentDeque() {
        // 和 ConcurrentLinkedQueue 一样，只是一个线程安全的 Deque，不具有任何阻塞接口
        ConcurrentLinkedDeque<Integer> queue = new ConcurrentLinkedDeque<>();
        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < 3; i++) {
            es.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    queue.offerFirst(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                }
                for (int j = 0; j < 10; j++) {
                    queue.offerLast(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                }
                for (int j = 0; j < 10; j++) {
                    System.out.println("poll " + queue.pollFirst());
                }
                for (int j = 0; j < 10; j++) {
                    System.out.println("poll " + queue.pollLast());
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
