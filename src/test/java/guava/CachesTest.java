package guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
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
}
