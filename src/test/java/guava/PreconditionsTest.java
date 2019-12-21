package guava;

import org.junit.Test;

import static com.google.common.base.Preconditions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PreconditionsTest {
    @Test
    public void testPreconditions() {
        final int i = 10;
        checkArgument(i >= 0, "Argument was %s but expected nonnegative", i);
        checkNotNull(i, "Argument should not be null");

        assertThrows(IllegalArgumentException.class, () -> checkArgument(false));
        assertDoesNotThrow(() -> checkArgument(true));

        assertThrows(NullPointerException.class, () -> checkNotNull(null));
        assertDoesNotThrow(() -> checkNotNull(10));

        assertThrows(IllegalStateException.class, () -> checkState(false));
        assertDoesNotThrow(() -> checkState(true));

        // checkElementIndex [0, size)
        assertThrows(IndexOutOfBoundsException.class, () -> checkElementIndex(11, 10));
        assertThrows(IndexOutOfBoundsException.class, () -> checkElementIndex(10, 10));
        assertDoesNotThrow(() -> checkElementIndex(0, 10));
        assertDoesNotThrow(() -> checkElementIndex(4, 10));

        // checkPositionIndex [0, size]
        assertThrows(IndexOutOfBoundsException.class, () -> checkPositionIndex(11, 10));
        assertDoesNotThrow(() -> checkPositionIndex(0, 10));
        assertDoesNotThrow(() -> checkPositionIndex(4, 10));
        assertDoesNotThrow(() -> checkPositionIndex(10, 10));

        // checkPositionIndexes [start, end) 在 [0, size] 之间
        assertThrows(IndexOutOfBoundsException.class, () -> checkPositionIndexes(3, 11, 10));
        assertDoesNotThrow(() -> checkPositionIndexes(3, 10, 10));
        assertDoesNotThrow(() -> checkPositionIndexes(3, 6, 10));
    }
}
