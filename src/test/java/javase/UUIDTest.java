package javase;

import org.junit.Test;

import java.util.UUID;

public class UUIDTest {
    @Test
    public void testUUID() {
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
    }
}
