package util;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OptionalTest {
    @Test
    public void testOptionalCreate() {
        {
            Optional<Integer> i1 = Optional.empty();
            Optional<Integer> i2 = Optional.ofNullable(null);
            Optional<Integer> i3 = Optional.ofNullable(6);
            Optional<Integer> i4 = Optional.of(6);
        }
        {
            assertFalse(Optional.<Integer>empty().isPresent());
            assertFalse(Optional.<Integer>ofNullable(null).isPresent());
            assertTrue(Optional.ofNullable(6).isPresent());
            assertTrue(Optional.of(6).isPresent());
        }
        {
            assertThrows(NoSuchElementException.class, Optional.<Integer>empty()::get);
            assertEquals(Optional.of(6).get(), Integer.valueOf(6));
        }
        {
            assertEquals(Optional.<Integer>empty().orElse(0), Integer.valueOf(0));
            assertEquals(Optional.of(6).orElse(0), Integer.valueOf(6));
            assertEquals(Optional.<Integer>empty().orElseGet(() -> 0), Integer.valueOf(0));
            assertEquals(Optional.of(6).orElseGet(() -> 0), Integer.valueOf(6));
        }
        {
            assertEquals(Optional.of(6).filter(i -> i % 2 == 0), Optional.of(6));
            assertEquals(Optional.of(6).filter(i -> i % 2 == 1), Optional.empty());
            assertEquals(Optional.of(6).map(i -> i.toString()), Optional.of("6"));
            assertEquals(Optional.of(6).flatMap(i -> Optional.of(i * i)), Optional.of(36));
        }
    }
}
