package guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BiMapTest {
    @Test
    public void testBiMap() {
        final BiMap<String, Integer> userID = HashBiMap.create();

        userID.put("Bob", 42);
        userID.put("Andy", 53);

        assertEquals(userID.get("Bob"), Integer.valueOf(42));
        assertEquals(userID.inverse().get(42), "Bob");
        assertTrue(userID.containsKey("Bob"));
        assertTrue(userID.values().contains(42));
    }
}
