package guava;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClassToInstanceMapTest {
    @Test
    public void testClassToInstanceMap() {
        ClassToInstanceMap<Number> numberDefault = MutableClassToInstanceMap.create();

        numberDefault.put(Integer.class, 10);
        numberDefault.put(Double.class, 123.456);

        assertEquals(numberDefault.get(Integer.class), 10);
        assertEquals(numberDefault.get(Double.class), 123.456);
    }
}
