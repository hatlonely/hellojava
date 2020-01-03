# java nio

nio (noblocking io) 非阻塞 io，提供了一套新的 io 处理框架，相比老的 io 流处理，增加了非阻塞的特性，提出了 `buffer`，`channel`，`selector` 三个概念

- `buffer`: 从通道中读取数据或者写入数据
- `channel`: 代表一个数据实体，比如文件或者套接字
- `selector`: 从多个非阻塞的 `channel` 选择一个可以处理

## buffer 方法

- `position`: 当前读取或者写入的位置
- `limit`: 有数据的最大位置
- `capacity`: buffer 的最大空间
- `mark`: 标记当前 position 的位置
- `reset`: 将 position 设置到回到上次标记的位置
- `clear`: 清空所有数据
- `compact`: 清空已读取的数据
- `rewind`: 重新开始读取
- `flip`: 切换到读模式

``` java
CharBuffer cb = CharBuffer.wrap("世上本没有路，走的人多了，也便成了路");
assertTrue(cb.hasRemaining());
assertEquals(cb.position(), 0);
assertEquals(cb.limit(), 18);
assertEquals(cb.capacity(), 18);
```

## nio 文件读写

``` java
{
    CharsetEncoder utf8 = Charset.forName("utf-8").newEncoder();
    RandomAccessFile out = new RandomAccessFile("/tmp/test.txt", "rw");
    FileChannel channel = out.getChannel();
    CharBuffer cb = CharBuffer.wrap("古之立大志者，不惟有超世之才，亦必有坚韧不拔之志");    // 读模式
    ByteBuffer bb = ByteBuffer.allocate(12);    // 写模式

    while (cb.hasRemaining()) {
        utf8.encode(cb, bb, true);
        bb.flip();
        channel.write(bb);
        bb.clear();
    }
    channel.close();
    out.close();
}
{
    CharsetDecoder utf8 = Charset.forName("utf-8").newDecoder();
    RandomAccessFile in = new RandomAccessFile("/tmp/test.txt", "r");
    FileChannel channel = in.getChannel();
    ByteBuffer bb = ByteBuffer.allocate(12);
    CharBuffer cb = CharBuffer.allocate(12);

    StringBuilder sb = new StringBuilder();
    for (int i = channel.read(bb); i != -1; i = channel.read(bb)) {
        bb.flip();  // 切换到读模式
        utf8.decode(bb, cb, true);   // 按 utf-8 解码
        cb.flip();  // 切换成到读模式
        while (cb.hasRemaining()) {
            sb.append(cb.get());
        }
        bb.compact();   // 切换到写模式，保留剩下未被读取的部分(这里有可能读取到不完整的 utf-8 字节)
        cb.clear();     // 切换到写模式，清空缓存区，丢弃未被读取的部分(这里没有未被读取的部分)
    }

    channel.close();
    in.close();
    assertEquals(sb.toString(), "古之立大志者，不惟有超世之才，亦必有坚韧不拔之志");
}
{
    CharsetDecoder utf8 = Charset.forName("utf-8").newDecoder();
    RandomAccessFile in = new RandomAccessFile("/tmp/test.txt", "r");
    FileChannel channel = in.getChannel();
    MappedByteBuffer bb = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
    CharBuffer cb = CharBuffer.allocate(12);
    bb.load();

    StringBuilder sb = new StringBuilder();
    while (bb.hasRemaining()) {
        utf8.decode(bb, cb, true);
        cb.flip();
        while (cb.hasRemaining()) {
            sb.append(cb.get());
        }
        cb.clear();
    }

    channel.close();
    in.close();
    assertEquals(sb.toString(), "古之立大志者，不惟有超世之才，亦必有坚韧不拔之志");
}
```


