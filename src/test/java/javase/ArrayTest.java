package javase;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.junit.Test;

public class ArrayTest {
    @Test
    public void testArray() {
        int[] iarr1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        assertEquals(iarr1[3], 3);
        assertEquals(iarr1.length, 10);

        // 填充
        int[] iarr2 = new int[5];
        Arrays.fill(iarr2, 6);
        assertArrayEquals(iarr2, new int[] { 6, 6, 6, 6, 6 });

        // 排序
        int[] iarr3 = { 5, 8, 1, 2, 0, 1 };
        Arrays.sort(iarr3);
        assertArrayEquals(iarr3, new int[] { 0, 1, 1, 2, 5, 8 });

        // 切片
        assertArrayEquals(Arrays.copyOf(iarr1, 5), new int[] { 0, 1, 2, 3, 4 });
        assertArrayEquals(Arrays.copyOfRange(iarr1, 3, 8), new int[] { 3, 4, 5, 6, 7 });
    }
}
