package util;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NioTest {
    @Test
    public void testBuffer() {
        CharBuffer cb = CharBuffer.wrap("世上本没有路，走的人多了，也便成了路");
        assertTrue(cb.hasRemaining());
        assertEquals(cb.position(), 0);
        assertEquals(cb.limit(), 18);
        assertEquals(cb.capacity(), 18);
    }

    @Test
    public void testNio() throws IOException {
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
    }
}
