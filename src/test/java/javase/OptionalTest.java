package javase;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OptionalTest {
    @Test
    public void testOptionalCreate() {
        {
            final Optional<Integer> i = Optional.empty();
            assertFalse(i.isPresent());
            assertThrows(NoSuchElementException.class, () -> i.get());
        }
        {
            final Optional<Integer> i = Optional.ofNullable(null);
            assertFalse(i.isPresent());
            assertThrows(NoSuchElementException.class, () -> i.get());
        }
        {
            final Optional<Integer> i = Optional.of(5);
            assertTrue(i.isPresent());
            assertEquals(i.get(), Integer.valueOf(5));
        }
        {
            final Optional<Integer> i = Optional.ofNullable(5);
            assertTrue(i.isPresent());
            assertEquals(i.get(), Integer.valueOf(5));
        }
    }

    @Test
    public void testOptionalGet() {
        {
            final Optional<Integer> i1 = Optional.empty();
            final Optional<Integer> i2 = Optional.of(5);

            assertEquals(i1.orElse(6), Integer.valueOf(6));
            assertEquals(i2.orElse(6), Integer.valueOf(5));

            assertEquals(i1.orElseGet(() -> 66), Integer.valueOf(66));
            assertEquals(i2.orElseGet(() -> 66), Integer.valueOf(5));
        }
        {
            final Optional<Integer> i5 = Optional.of(5);
            final Optional<Integer> i6 = Optional.of(6);
            assertEquals(i5.filter((i) -> i % 2 == 0), Optional.empty());
            assertEquals(i6.filter((i) -> i % 2 == 0), i6);
            assertEquals(i5.map((i) -> i * i), Optional.of(25));
            assertEquals(i6.flatMap((i) -> Optional.of(i * i)), Optional.of(36));
        }
    }
}
