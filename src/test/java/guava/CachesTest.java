package guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.Weigher;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class CachesTest {
    @Test
    public void testCache() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();

        for (int i = 0; i < 10; i++) {
            cache.put("key" + i, "val" + i);
        }
        System.out.println(cache.asMap());

        assertEquals(cache.getIfPresent("key1"), null);
    }

    @Test
    public void testOnRemoval() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .removalListener((n) -> System.out.println("remove " + n.getKey()))
                .build();

        for (int i = 0; i < 10; i++) {
            cache.put("key" + i, "val" + i);
        }
    }

    @Test
    public void testWeakValues() {
        Cache<String, Object> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .weakValues()
                .build();

        Object obj = new Object();
        cache.put("key", obj);
        assertEquals(cache.getIfPresent("key"), obj);
        System.out.println(cache.asMap());
        obj = new Object();
        System.gc();
        assertEquals(cache.getIfPresent("key"), null);
        System.out.println(cache.asMap());
    }

    @Test
    public void testWeight() {
        // 设置权值，当权值大于最大权值时，执行删除
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumWeight(10)
                .removalListener((n) -> System.out.println("remove " + n.getKey()))
                .weigher((Weigher<String, String>) (k, v) -> k.length())
                .build();

        cache.put("key111", "val111");
        cache.put("key1", "val1");
        cache.put("key11", "val11");
        cache.put("key2", "val2");
        cache.put("key", "val2");
    }
}
