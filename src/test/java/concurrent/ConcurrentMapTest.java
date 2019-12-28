package concurrent;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentMapTest {
    @Test
    public void testConcurrentMap() {
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

            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    for (int j = 0; j < 100; j++) {
                        System.out.println(map.get("key" + j));
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
}
