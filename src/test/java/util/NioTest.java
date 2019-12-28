package util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NioTest {
    private static void createFile() throws IOException {
        NioTest.deleteFile();
        BufferedWriter bw = Files.newBufferedWriter(Paths.get("/tmp/test.txt"));
        bw.write("果壳里的宇宙");
        bw.newLine();
        bw.write("时间简史");
        bw.newLine();
        bw.close();
    }

    private static void deleteFile() throws IOException {
        Files.deleteIfExists(Paths.get("/tmp/test.txt"));
    }

    @Before
    public void setUp() throws IOException {
        NioTest.createFile();
    }

    @After
    public void tearDown() throws IOException {
        NioTest.deleteFile();
    }

    @Test
    public void testNio() throws IOException {
        FileInputStream in = new FileInputStream("/tmp/test.txt");
        FileChannel channel = in.getChannel();
        ByteBuffer bb = ByteBuffer.allocate(12);
        CharBuffer cb = CharBuffer.allocate(12);
        CharsetDecoder decoder = Charset.forName("utf-8").newDecoder();

        for (int i = channel.read(bb); i != -1; i = channel.read(bb)) {
            bb.flip();  // 读模式切换成写模式
            decoder.decode(bb, cb, true);   // 按 utf-8 解码
            cb.flip();  // 读模式切换成写模式
            while (cb.hasRemaining()) {
                System.out.println(cb.get());
            }
            bb.compact();   // 切换到写模式，保留剩下未被读取的部分(这里有可能读取到不完整的 utf-8 字节)
            cb.clear();     // 切换到写模式，清空缓存区，丢弃未被读取的部分(这里没有未被读取的部分)
        }
    }
}
