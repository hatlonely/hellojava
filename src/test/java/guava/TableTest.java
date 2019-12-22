package guava;

import com.google.common.collect.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TableTest {
    @Test
    public void testTable() {
        Table<String, String, Integer> t1 = HashBasedTable.create();
        Table<String, String, Integer> t2 = TreeBasedTable.create();

        for (Table<String, String, Integer> t : ImmutableList.of(t1, t2)) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    t.put("row" + i, "col" + j, (i + 1) * (j + 1));
                }
            }

            assertFalse(t.isEmpty());
            assertEquals(t.size(), 9);
            assertTrue(t.contains("row1", "col1"));
            assertTrue(t.containsRow("row1"));
            assertTrue(t.containsColumn("col1"));
            assertTrue(t.containsValue(4));
            assertEquals(t.get("row1", "col1"), Integer.valueOf(4));
            assertEquals(t.row("row1").get("col1"), Integer.valueOf(4));
            assertEquals(t.column("col1").get("row1"), Integer.valueOf(4));
        }
    }

    @Test
    public void testSortedTable() {
        RowSortedTable<String, String, Integer> t = TreeBasedTable.create();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                t.put("row" + i, "col" + j, (i + 1) * (j + 1));
            }
        }

        assertEquals(t.rowKeySet().first(), "row0");
        assertEquals(t.rowKeySet().last(), "row2");
    }
}
