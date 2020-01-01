# java io 流

## io 流总览

![io 流总览](io.png)

io 流主要提供四个接口

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

`InputStream` 提供如下接口:

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

`OutputStream` 提供如下接口:

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

`Reader` 提供如下接口:

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

`Writer` 提供如下接口:

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

## 字节文件流

文件流关注文件的读取和写入

``` java
{
    FileOutputStream fout = new FileOutputStream("/tmp/test.txt");
    fout.write("No patient who, who has no wisdom".getBytes());
    fout.close();
}
{
    FileInputStream fin = new FileInputStream("/tmp/test.txt");
    assertArrayEquals(fin.readAllBytes(), "No patient who, who has no wisdom".getBytes());
    fin.close();
}
```

## 缓冲字节流

缓冲字节流采用装饰者模式，装饰在其他流上，使流拥有了缓存功能，从而提高读写了效率

``` java
{
    BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream("/tmp/test.txt"));
    bout.write("People lack the willpower, rather than strength".getBytes());
    bout.close();
}
{
    BufferedInputStream bin = new BufferedInputStream(new FileInputStream("/tmp/test.txt"));
    assertArrayEquals(bin.readAllBytes(), "People lack the willpower, rather than strength".getBytes());
    bin.close();
}
```

## 二进制字节流

二进制字节流关注在基本数据类型的读取和写入，采用装饰者模式，能装饰在其他流上

`DataOutputStream` 在 `OutputStream` 的基础上新增了如下接口:

- `writeBoolean`: 写入一个 boolean 值
- `writeByte`: 写入一个字节
- `writeShort`: 写入一个短整型
- `writeInt`: 写入一个整型
- `writeLong`: 写入一个长整型
- `writeFloat`: 写入一个浮点型
- `writeDouble`: 写入一个双精度浮点型
- `writeChar`: 写入一个字符
- `writeUTF`: 写入一个 unicode 字符串

`DataInputStream` 在 `InputStream` 的基础上新增了如下接口:

- `readBoolean`: 读取一个 boolean 值
- `readByte`: 读取一个字节
- `readShort`: 读取一个 short
- `readInt`: 读取一个整型
- `readLong`: 读取一个长整型
- `readFloat`: 读取一个浮点型
- `readDouble`: 读取一个双精度浮点型
- `readChar`: 读取一个字符
- `readUTF`: 读取一个 unicode 字符串

``` java
{
    DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("/tmp/test.txt")));
    dout.writeBoolean(false);
    dout.writeByte('x');
    dout.writeShort(123);
    dout.writeInt(123456);
    dout.writeLong(123456789);
    dout.writeFloat((float) 123.456);
    dout.writeDouble(123.456);
    dout.writeUTF("Rome wasn’t built in one day");
    dout.close();
}
{
    DataInputStream din = new DataInputStream(new BufferedInputStream(new FileInputStream("/tmp/test.txt")));
    assertEquals(din.readBoolean(), false);
    assertEquals(din.readByte(), 'x');
    assertEquals(din.readShort(), 123);
    assertEquals(din.readInt(), 123456);
    assertEquals(din.readLong(), 123456789);
    assertEquals(din.readFloat(), (float) 123.456);
    assertEquals(din.readDouble(), 123.456);
    assertEquals(din.readUTF(), "Rome wasn’t built in one day");
    din.close();
}
```

## 对象字节流

对象字节流关注对象的写入和读取，同时拥有二进制字节流的所有功能，同样采用装饰者模式

`ObjectOutputStream` 相比 `DataOutputStream` 新增了如下接口:

- `writeObject`: 写入任何 Serializable 对象

`ObjectInputStream` 相比 `DataInputStream` 新增了如下接口:

- `readObject`: 从流中读取一个对象

``` java
{
    ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("/tmp/test.txt")));
    oout.writeBoolean(false);
    oout.writeByte('x');
    oout.writeShort(123);
    oout.writeInt(123456);
    oout.writeLong(123456789);
    oout.writeFloat((float) 123.456);
    oout.writeDouble(123.456);
    oout.writeUTF("Nothing is impossible to a willing heart");
    oout.writeObject(new Point(123, 456));
    oout.close();
}
{
    ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(new FileInputStream("/tmp/test.txt")));
    assertEquals(oin.readBoolean(), false);
    assertEquals(oin.readByte(), 'x');
    assertEquals(oin.readShort(), 123);
    assertEquals(oin.readInt(), 123456);
    assertEquals(oin.readLong(), 123456789);
    assertEquals(oin.readFloat(), (float) 123.456);
    assertEquals(oin.readDouble(), 123.456);
    assertEquals(oin.readUTF(), "Nothing is impossible to a willing heart");
    Point point = (Point) oin.readObject();
    assertEquals(point.x, 123);
    assertEquals(point.y, 456);
    oin.close();
}
```

## 可回退字节流

可回退字节流内部维护了一个固定大小的缓冲区(可通过构造函数配置 buffer 的大小)，允许将字节回退到缓冲区，如果超过了缓冲区大小，会抛出异常

`PushbackInputStream` 在 `InputStream` 的基础上新增了如下接口:

- `unread`: 回退一个字节
- `unread(buffer)`: 将 buffer 中的数据回退到流的缓冲区
- `unread(buffer, offset, length)`: 从 buffer 的 offset 处回退 length 个字节到流缓冲区

``` java
PushbackInputStream pin = new PushbackInputStream(new ByteArrayInputStream("Failure is the mother of success".getBytes()), 10);
byte[] buf = new byte[7];
assertEquals(pin.read(buf), 7);
assertArrayEquals(buf, "Failure".getBytes());
pin.unread(buf);
assertEquals(pin.read(buf), 7);
assertArrayEquals(buf, "Failure".getBytes());
// 超过 buffer 的大小，抛出 IOException
assertThrows(IOException.class, () -> pin.unread("01234567890".getBytes()));
```

## SequenceInputStream

`SequenceInputStream` 将多个 `InputStream` 合并成一个

``` java
InputStream in1 = new ByteArrayInputStream("For man is man and master of his fate\n".getBytes());
InputStream in2 = new ByteArrayInputStream("Cease to struggle and you cease to live\n".getBytes());
Vector<InputStream> vi = new Vector<>(List.of(in1, in2));
SequenceInputStream sin = new SequenceInputStream(vi.elements());
assertArrayEquals(sin.readAllBytes(), "For man is man and master of his fate\nCease to struggle and you cease to live\n".getBytes());
```

## 管道字节流

`PipedInputStream` 和 `PipedOutputStream` 通过调用 `connect` 方法建立连接，往 `PipedOutputStream` 写入，能从 `PipedInputStream` 读取，这种管道模式是一对一的，对一个管道流建立两次连接会抛出异常

`PipedOutputStream` 在 `OutputStream` 的基础上提供如下接口:

- `connect`: 与一个 `PipedInputStream` 建立连接，如果已经建立连接，将抛出异常

`PipedInputStream` 在 `InputStream` 的基础上提供如下接口:

- `connect`: 与一个 `PipedOutputStream` 建立连接，如果已经建立连接，将抛出异常

``` java
ExecutorService es = Executors.newCachedThreadPool();
PipedInputStream pin = new PipedInputStream();
PipedOutputStream pout = new PipedOutputStream();
pin.connect(pout);

es.execute(() -> {
    try {
        ObjectOutputStream oout = new ObjectOutputStream(pout);
        oout.writeInt(123456);
        oout.writeUTF("如果你还没能找到让自己热爱的事业，继续寻找，不要放弃");
        oout.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
});

es.execute(() -> {
    try {
        ObjectInputStream oin = new ObjectInputStream(pin);
        assertEquals(oin.readInt(), 123456);
        assertEquals(oin.readUTF(), "如果你还没能找到让自己热爱的事业，继续寻找，不要放弃");
        oin.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
});

try {
    es.shutdown();
    while (!es.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
        // nothing to do
    }
} catch (Exception e) {
    e.printStackTrace();
}
```

## 链接

- 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/util/IOTest.java>
