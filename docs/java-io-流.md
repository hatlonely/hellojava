# java io 流

## io 流总览

![io 流总览](io.png)

IO 流主要提供四个接口

- `InputStream`: 输入字节流
- `OutputStream`: 输出字节流
- `Reader`: 输入字符流
- `Writer`: 输出字符流

## InputStream

`InputStream` 输入字节流，关注字节的读取，io 包提供如下 InputStream 的实现

- `ByteArrayInputStream`: 字节数组输入流
- `FileInputStream`: 文件字节输入流
- `PipedInputStream`: 管道输入流，可和其他的 PipedOutStream 连接，通常用于线程间通信
- `DataInputStream`: 二进制数据输入流
- `ObjectInputStream`: 对象输入流
- `BufferedInputStream`: 带缓冲 buffer 的字节输入流
- `SequenceInputStream`: 能将多个字节流合并成一个
- `PushbackInputStream`: 能回退的字节流

InputStream 提供如下接口:

- `read`: 从流中读取一个字节
- `read(buffer)`: 从流中读取字节到 buffer 中，返回真实读取的字节数
- `read(buffer, offset, length)`: 从流中读取 length 个字节，写入到 buffer 的 offset 处，返回真实读取的字节数
- `readNBytes(buffer, offset, length)`: 和 read 一样，但是保证读取 length 个字节，除非流中没有数据
- `readAllBytes`: 读取所有字节，返回一个字节数组
- `skip`: 跳过前 n 个字节
- `available`: 剩余字节数
- `mark`: 标记当前读取的位置
- `reset`: 将流指针重置到上次标记的位置
- `close`: 关闭流，释放资源

``` java
{
    InputStream in = new ByteArrayInputStream("0123456789".getBytes());
    assertEquals(in.read(), '0');
}
{
    InputStream in = new ByteArrayInputStream("0123456789".getBytes());
    byte[] buf = new byte[4];
    assertEquals(in.read(buf), 4);
    assertArrayEquals(buf, "0123".getBytes());
}
{
    InputStream in = new ByteArrayInputStream("0123456789".getBytes());
    byte[] buf = new byte[20];
    assertEquals(in.read(buf), 10);
    assertArrayEquals(Arrays.copyOf(buf, 10), "0123456789".getBytes());
}
{
    InputStream in = new ByteArrayInputStream("0123456789".getBytes());
    byte[] buf = new byte[20];
    assertEquals(in.read(buf, 1, 4), 4);
    assertArrayEquals(Arrays.copyOfRange(buf, 1, 1 + 4), "0123".getBytes());
}
{
    InputStream in = new ByteArrayInputStream("0123456789".getBytes());
    byte[] buf = new byte[20];
    assertEquals(in.readNBytes(buf, 1, 4), 4);
    assertArrayEquals(Arrays.copyOfRange(buf, 1, 1 + 4), "0123".getBytes());
}
{
    InputStream in = new ByteArrayInputStream("0123456789".getBytes());
    assertArrayEquals(in.readAllBytes(), "0123456789".getBytes());
}
{
    InputStream in = new ByteArrayInputStream("0123456789".getBytes());
    assertEquals(in.skip(2), 2);
    assertEquals(in.available(), 8);
    assertEquals(in.read(), '2');
    assertEquals(in.available(), 7);
    in.mark(0);
    assertEquals(in.read(), '3');
    in.reset();
    assertEquals(in.available(), 7);
    assertEquals(in.read(), '3');
    in.close();
}
{
    InputStream in = new ByteArrayInputStream("0123456789".getBytes());
    for (int ch = in.read(); ch != -1; ch = in.read()) {
        System.out.println(ch);
    }
}
```

## OutputStream

`OutputStream` 输出字节流，关注字节的写入，io 包提供了如下 OutputStream 的实现

- `ByteArrayOutputStream`: 输出 byte 数组
- `FileOutputStream`: 文件输出流
- `PipedOutputStream`: 管道输出流，可和其他的 PipedInputStream 连接，通常用于线程间通信
- `DataOutputStream`: 二进制数据输出流
- `ObjectOutputStream`: 对象输出流
- `BufferedOutputStream`: 带缓冲 buffer 的输出流
- `SequenceOutputStream`: 能将多个输出流合并成一个

OutputStream 提供如下接口:

- `write`: 写入一个字节
- `write(buffer)`: 写入 buffer 中的数据
- `write(buffer, offset, length)`: 写入 buffer 从 offset 起的 length 个字节的数据
- `flush`: 将缓冲区的数据刷到实际的存储中
- `close`: 关闭流

``` java
OutputStream out = new ByteArrayOutputStream();
out.write('0');
out.write("123456789".getBytes());
out.write("0123456789".getBytes(), 1, 2);
out.flush();
out.close();
```

## Reader

`Reader` 字符输入流，关注字符的读取，io 包提供如下 `Reader` 的实现

- `CharArrayReader`: 字符数组输入流
- `FileReader`: 文件字符输入流
- `PipedReader`: 管道输入流，可以和 `PipedWriter` 连接，通常用于线程间通信
- `StringReader`: 字符串输入流
- `BufferedReader`: 带缓冲 buffer 的字符输入流
- `LineNumberReader`: 带行号的字符输入流
- `PushbackReader`: 能回退的字符输入流

Reader 提供如下接口:

- `read`: 从流中读取一个字符
- `read(buffer)`: 从流中读取字符到 buffer 中，返回真实读取的字符数
- `read(buffer, offset, length)`: 从流中读取 length 个字符，写入到 buffer 的 offset 处，返回真实读取的字符数
- `read(CharBuffer`: 从流中读取字符到 CharBuffer 中，返回真实读取的字符数
- `skip`: 跳过前 n 个字符
- `mark`: 标记当前读取的位置
- `reset`: 将流指针重置到上次标记的位置
- `close`: 关闭流，释放资源

``` java
{
    Reader reader = new CharArrayReader("0123456789".toCharArray());
    assertEquals(reader.read(), '0');
}
{
    Reader reader = new CharArrayReader("0123456789".toCharArray());
    char[] buf = new char[4];
    assertEquals(reader.read(buf), 4);
    assertArrayEquals(buf, "0123".toCharArray());
}
{
    Reader reader = new CharArrayReader("0123456789".toCharArray());
    char[] buf = new char[20];
    assertEquals(reader.read(buf), 10);
    assertArrayEquals(Arrays.copyOf(buf, 10), "0123456789".toCharArray());
}
{
    Reader reader = new CharArrayReader("0123456789".toCharArray());
    char[] buf = new char[20];
    assertEquals(reader.read(buf, 1, 4), 4);
    assertArrayEquals(Arrays.copyOfRange(buf, 1, 1 + 4), "0123".toCharArray());
}
{
    Reader reader = new CharArrayReader("0123456789".toCharArray());
    CharBuffer buf = CharBuffer.allocate(20);
    assertEquals(reader.read(buf), 10);
}
{
    Reader reader = new CharArrayReader("0123456789".toCharArray());
    assertTrue(reader.ready());
    assertEquals(reader.skip(2), 2);
    assertEquals(reader.read(), '2');
    reader.mark(0);
    assertEquals(reader.read(), '3');
    reader.reset();
    assertEquals(reader.read(), '3');
    reader.close();
}
{
    Reader reader = new CharArrayReader("0123456789".toCharArray());
    for (int ch = reader.read(); ch != -1; ch = reader.read()) {
        System.out.println(ch);
    }
}
```

## Writer

`Writer` 字符输出流，关注字符的写入，io 包提供如下 `Writer` 的实现

- `CharArrayWriter`: 字符数组输出流
- `FileWriter`: 文件字符输出流
- `PipedWriter`: 管道输出流，可以和 `PipedReader` 连接，通常用于线程间通信
- `StringWriter`: 字符串输出流
- `BufferedWriter`: 带缓冲 buffer 的字符输出流

Writer 提供如下接口:

- `write(char)`: 写入一个字符
- `write(string)`: 写入一个字符串
- `write(string, offset, length)`: 写入 string 从 offset 起的 length 个字符的数据
- `write(char[])`: 写入字符数组中的数据
- `write(char[], offset, length)`: 写入字符数组从 offset 起的 length 个字符的数据
- `append(ch)`: 写入一个字符，和 write 一样
- `append(CharSequence)`: 写入字符序列的所有数据(String, StringBuilder, StringBuffer 都是 CharSequence 的子类)
- `append(CharSequence, offset, length)`: 写入字符序列从 offset 起的 length 个字符的数据
- `flush`: 将缓冲区的数据刷到实际的存储中
- `close`: 关闭流

``` java
Writer writer = new CharArrayWriter();
writer.write('0');
writer.write("0123456789");
writer.write("0123456789", 1, 4);
writer.write("0123456789".toCharArray());
writer.write("0123456789".toCharArray(), 1, 4);
writer.append('0');
writer.append(new StringBuilder("0123456789"));
writer.append(new StringBuilder("0123456789"), 1, 4);
writer.flush();
writer.close();
```

## 链接

- 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/util/IOTest.java>
