# java 字符串

## 断言

- `startsWith(x)`: 前缀判断
- `endsWith(x)`: 后缀判断
- `contains(x)`: 包含判断
- `equalsIgnoreCase(x)`: 忽略大小写的判断相等

``` java
assertTrue("stay hungry, stay foolish".startsWith("stay"));
assertTrue("stay hungry, stay foolish".endsWith("foolish"));
assertTrue("stay hungry, stay foolish".contains("hungry"));
assertTrue("hello world".equalsIgnoreCase("Hello world"));
```

## 字符串查找

``` java
assertEquals("01234567890123456789".indexOf('6'), 6);
assertEquals("01234567890123456789".lastIndexOf('6'), 16);
assertEquals("01234567890123456789".indexOf("678"), 6);
assertEquals("01234567890123456789".lastIndexOf("678"), 16);
assertEquals("01234567890123456789".indexOf("abcd"), -1);
assertEquals("01234567890123456789".charAt(6), '6');
```

## 字符串操作

- `substring`: 子字符串
- `trim`: 去掉前后空白
- `split`: 字符串分割成字符串数组
- `join`: 字符串合并

``` java
assertEquals("hello" + " " + "java", "hello java");
assertEquals("HELLO".toLowerCase(), "hello");
assertEquals("world".toUpperCase(), "WORLD");
assertEquals(" hello world ".trim(), "hello world");
assertEquals("0123456789".substring(4), "456789");
assertEquals("0123456789".substring(3, 6), "345");
assertEquals("stay hungry, stay foolish".replace("stay", "keep"), "keep hungry, keep foolish");
assertArrayEquals("java golang swift".split(" "), new String[]{"java", "golang", "swift"});
assertEquals(String.join("|", new String[]{"java", "golang", "swift"}), "java|golang|swift");
```

## 字符串转化

``` java
assertEquals(Integer.parseInt("123456"), 123456);
assertEquals(Double.parseDouble("123.456"), 123.456, 0.00001);
assertEquals(Integer.toString(123456), "123456");
assertEquals(Integer.toHexString(123456), "1e240");
assertEquals(Double.toString(123.456), "123.456");
```

## 字符串构造

java 的 `String` 是不可变的，可以通过 `StringBuilder`(多线程场景下可以使用 `StringBuffer`) 来构造一个字符串

``` java
StringBuilder sb = new StringBuilder();
sb.append("hello");
sb.append(" ");
sb.append("world");
sb.append(" ");
sb.append(123);
assertEquals(sb.toString(), "hello world 123");
```

## 链接

- 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/util/StringTest.java>
