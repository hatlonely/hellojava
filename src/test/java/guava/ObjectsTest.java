package guava;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;
import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.Assert.*;

public class ObjectsTest {
    @Test
    public void testEquals() {
        assertTrue(Objects.equals("a", "a"));
        assertTrue(Objects.equals(null, null));
        assertFalse(Objects.equals(null, "a"));
        assertFalse(Objects.equals("a", null));
    }

    @Test
    public void testHash() {
        System.out.println(Objects.hash("a", "b", Arrays.asList("a", "b")));
    }

    @Test
    public void testToString() {
        assertEquals(
                MoreObjects.toStringHelper("MyClass").add("x", 1).toString(),
                "MyClass{x=1}"
        );

        class MyClass {
            private final int x;

            public MyClass(final int x) {
                this.x = x;
            }

            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this).add("x", x).toString();
            }
        }
        assertEquals(new MyClass(1).toString(), "MyClass{x=1}");
    }

    @Test
    public void testCompare() {
        class Person {
            private final String firstName;
            private final String lastName;
            private final int zipCode;

            public Person(final String firstName, final String lastName, final int zipCode) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.zipCode = zipCode;
            }

            public int compareTo(final Person other) {
                return ComparisonChain.start()
                        .compare(firstName, other.firstName)
                        .compare(lastName, other.lastName)
                        .compare(zipCode, other.zipCode).result();
            }
        }

        final Person p1 = new Person("Steven", "Jobs", 1000);
        final Person p2 = new Person("Elon", "Musk", 10001);
        assertTrue(p1.compareTo(p2) > 0);
    }
}
