package util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ArrayTest {
    @Test
    public void testArray() {
        {
            int[] ia = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            assertEquals(ia[3], 3);
            assertEquals(ia.length, 10);
        }

        {
            int[] ia = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            assertEquals(Arrays.binarySearch(ia, 5), 5);
        }

        {
            int[] ia = new int[5];
            Arrays.fill(ia, 6);
            assertArrayEquals(ia, new int[]{6, 6, 6, 6, 6});
        }

        {
            int[] ia = {5, 8, 1, 2, 0, 1};
            Arrays.sort(ia);
            assertArrayEquals(ia, new int[]{0, 1, 1, 2, 5, 8});
        }

        {
            int[] ia = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            assertArrayEquals(Arrays.copyOf(ia, 5), new int[]{0, 1, 2, 3, 4});
            assertArrayEquals(Arrays.copyOfRange(ia, 3, 8), new int[]{3, 4, 5, 6, 7});
        }
    }
}
