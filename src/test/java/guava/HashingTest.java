package guava;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HashingTest {
    @Test
    public void testHashing() {
        for (HashFunction h : ImmutableList.of(
                Hashing.md5(),
                Hashing.murmur3_32(),
                Hashing.murmur3_128(),
                Hashing.sha256(),
                Hashing.sha512(),
                Hashing.goodFastHash(512),
                Hashing.crc32(),
                Hashing.crc32c()
        )) {
            HashCode c = h.newHasher()
                    .putString("hello", Charsets.UTF_8)
                    .putLong(1234)
                    .putBoolean(false)
                    .putInt(10)
                    .putFloat(123.456f)
                    .hash();
            System.out.println(c);
        }
    }

    @Test
    public void testBloomFilter() {
        class Person {
            private final int id;
            private final String name;
            private final int age;

            private Person(int id, String name, int age) {
                this.id = id;
                this.name = name;
                this.age = age;
            }
        }

        BloomFilter<Person> friends = BloomFilter.create(new Funnel<Person>() {
            private static final long serialVersionUID = 2488344353632170981L;

            @Override
            public void funnel(Person p, PrimitiveSink into) {
                into.putInt(p.id).putString(p.name, Charsets.UTF_8).putInt(p.age);
            }
        }, 500, 0.01);  // 总共人数是 500，判断存在失败的概率为 0.1%

        Person person = new Person(1, "jack", 10);
        friends.put(person);

        assertTrue(friends.mightContain(person));
    }
}
