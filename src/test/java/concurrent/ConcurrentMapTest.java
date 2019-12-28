package concurrent;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.*;

public class ConcurrentMapTest {
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
}
