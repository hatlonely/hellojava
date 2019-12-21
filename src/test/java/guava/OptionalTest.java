package guava;

import com.google.common.base.Optional;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OptionalTest {
    @Test
    public void testOptionalCreate() {
        final Optional<Integer> i1 = Optional.absent();
        final Optional<Integer> i2 = Optional.of(5);
        final Optional<Integer> i3 = Optional.fromNullable(null);
        final Optional<Integer> i4 = Optional.fromNullable(5);

        assertFalse(i1.isPresent());
        assertTrue(i2.isPresent());
        assertFalse(i3.isPresent());
        assertTrue(i4.isPresent());
        assertThrows(IllegalStateException.class, () -> i1.get());
        assertEquals(i2.get(), Integer.valueOf(5));
        assertThrows(IllegalStateException.class, () -> i3.get());
        assertEquals(i4.get(), Integer.valueOf(5));
    }

    @Test
    public void testOptionalGet() {
        final Optional<Integer> i1 = Optional.absent();
        final Optional<Integer> i2 = Optional.of(5);

        assertEquals(i1.or(6), Integer.valueOf(6));
        assertEquals(i2.or(6), Integer.valueOf(5));
        assertEquals(i1.orNull(), null);
        assertEquals(i2.orNull(), Integer.valueOf(5));

        assertEquals(i2.transform((i) -> (i * i)).orNull(), Integer.valueOf(25));
        assertEquals(i1.or(() -> 66), Integer.valueOf(66));
    }
}
