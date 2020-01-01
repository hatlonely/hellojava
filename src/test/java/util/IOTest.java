package util;

import com.google.common.base.Charsets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class Point implements Serializable {
    private static final long serialVersionUID = 1420672609912364160L;
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}


public class IOTest {
    private static void createFile() throws IOException {
        IOTest.deleteFile();
        BufferedWriter bw = Files.newBufferedWriter(Paths.get("/tmp/test.txt"));
        bw.write("hello world");
        bw.newLine();
        bw.close();
    }

    private static void deleteFile() throws IOException {
        Files.deleteIfExists(Paths.get("/tmp/test.txt"));
    }

    @Before
    public void setUp() throws IOException {
        IOTest.createFile();
    }

    @After
    public void tearDown() throws IOException {
        IOTest.deleteFile();
    }

    @Test
    public void testInputStream() throws IOException {
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
    }

    @Test
    public void testOutputStream() throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        out.write('0');
        out.write("123456789".getBytes());
        out.write("0123456789".getBytes(), 1, 2);
        out.flush();
        out.close();
    }

    @Test
    public void testReader() throws IOException {
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
    }

    @Test
    public void testWriter() throws IOException {
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
    }

    @Test
    public void testFileStream() throws IOException {
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
    }

    @Test
    public void testBufferedStream() throws IOException {
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
    }

    @Test
    public void testDataStream() throws IOException {
        {
            DataOutputStream dout = new DataOutputStream(new FileOutputStream("/tmp/test.txt"));
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
            DataInputStream din = new DataInputStream(new FileInputStream("/tmp/test.txt"));
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
    }

    @Test
    public void testObjectStream() throws IOException, ClassNotFoundException {
        {
            ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream("/tmp/test.txt"));
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
            ObjectInputStream oin = new ObjectInputStream(new FileInputStream("/tmp/test.txt"));
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
    }

    @Test
    public void testSequenceInputStream() throws IOException {
        InputStream in1 = new ByteArrayInputStream("For man is man and master of his fate\n".getBytes());
        InputStream in2 = new ByteArrayInputStream("Cease to struggle and you cease to live\n".getBytes());
        Vector<InputStream> vi = new Vector<>(List.of(in1, in2));
        SequenceInputStream sin = new SequenceInputStream(vi.elements());
        assertArrayEquals(sin.readAllBytes(), "For man is man and master of his fate\nCease to struggle and you cease to live\n".getBytes());
    }

    @Test
    public void testPushbackInputStream() throws IOException {
        PushbackInputStream pin = new PushbackInputStream(new ByteArrayInputStream("Failure is the mother of success".getBytes()), 10);
        byte[] buf = new byte[7];
        assertEquals(pin.read(buf), 7);
        assertArrayEquals(buf, "Failure".getBytes());
        pin.unread(buf);
        assertEquals(pin.read(buf), 7);
        assertArrayEquals(buf, "Failure".getBytes());
        // 超过 buffer 的大小，抛出 IOException
        assertThrows(IOException.class, () -> pin.unread("01234567890".getBytes()));
    }

    @Test
    public void testPushbackReader() throws IOException {
        PushbackReader pr = new PushbackReader(new StringReader("蚍蜉撼大树,可笑不自量"), 10);
        char[] buf = new char[5];
        assertEquals(pr.read(buf), 5);
        assertArrayEquals(buf, "蚍蜉撼大树".toCharArray());
        pr.unread(buf);
        assertEquals(pr.read(buf), 5);
        assertArrayEquals(buf, "蚍蜉撼大树".toCharArray());
        // 超过 buffer 的大小，抛出 IOException
        assertThrows(IOException.class, () -> pr.unread("01234567890".toCharArray()));
    }

    @Test
    public void testFileReaderWriter() throws IOException {
        {
            FileWriter fw = new FileWriter("/tmp/test.txt");
            assertEquals(fw.getEncoding(), "UTF8");
            System.out.println(fw.getEncoding());
            fw.write("初学者的心态；拥有初学者的心态是件了不起的事情");
            fw.flush();
            fw.close();
        }
        {
            FileReader fr = new FileReader("/tmp/test.txt");
            assertEquals(fr.getEncoding(), "UTF8");
            StringBuilder sb = new StringBuilder();
            for (int ch = fr.read(); ch != -1; ch = fr.read()) {
                sb.append((char) ch);
            }
            assertEquals(sb.toString(), "初学者的心态；拥有初学者的心态是件了不起的事情");
            fr.close();
        }
    }

    @Test
    public void testStreamReaderWriter() throws IOException {
        {
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream("/tmp/test.txt"), "utf-8");
            ow.write("你究竟是想一辈子卖糖水，还是希望获得改变世界的机遇");
            ow.flush();
            ow.close();
        }
        {
            InputStreamReader rw = new InputStreamReader(new FileInputStream("/tmp/test.txt"), "utf-8");
            StringBuilder sb = new StringBuilder();
            for (int ch = rw.read(); ch != -1; ch = rw.read()) {
                sb.append((char) ch);
            }
            assertEquals(sb.toString(), "你究竟是想一辈子卖糖水，还是希望获得改变世界的机遇");
            rw.close();
        }
    }

    @Test
    public void testBufferedReaderWriter() throws IOException {
        {
            // BufferedWriter bw = new BufferedWriter(new FileWriter("/tmp/test.txt"));
            BufferedWriter bw = Files.newBufferedWriter(Paths.get("/tmp/test.txt"), Charsets.UTF_8);
            bw.write("穷则独善其身，达则兼济天下");
            bw.newLine();
            bw.write("玉不琢、不成器，人不学、不知义");
            bw.newLine();
            bw.close();
        }
        {
            // BufferedReader br = new BufferedReader(new FileReader("/tmp/test.txt"));
            BufferedReader br = Files.newBufferedReader(Paths.get("/tmp/test.txt"), Charsets.UTF_8);
            assertEquals(br.readLine(), "穷则独善其身，达则兼济天下");
            assertEquals(br.readLine(), "玉不琢、不成器，人不学、不知义");
            assertEquals(br.readLine(), null);
            br.close();
        }
        {
            // BufferedReader br = new BufferedReader(new FileReader("/tmp/test.txt"));
            BufferedReader br = Files.newBufferedReader(Paths.get("/tmp/test.txt"), Charsets.UTF_8);
            assertThat(br.lines().collect(Collectors.toList()), equalTo(List.of(
                    "穷则独善其身，达则兼济天下",
                    "玉不琢、不成器，人不学、不知义"
            )));
            br.close();
        }
    }

    @Test
    public void testStringReaderWriter() throws IOException {
        {
            StringWriter sw = new StringWriter();
            sw.write("学而不思则罔，思而不学则殆");
            assertEquals(sw.getBuffer().toString(), "学而不思则罔，思而不学则殆");
            sw.close();
        }
        {
            StringReader sr = new StringReader("一年之计在于春，一日之计在于晨");
            StringBuilder sb = new StringBuilder();
            for (int ch = sr.read(); ch != -1; ch = sr.read()) {
                sb.append((char) ch);
            }
            assertEquals(sb.toString(), "一年之计在于春，一日之计在于晨");
        }
    }

    @Test
    public void testLineNumberReader() throws IOException {
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter("/tmp/test.txt"));
            bw.write("富贵不能淫\n贫贱不能移\n威武不能屈\n此之谓大丈夫\n");
            bw.close();
        }
        {
            LineNumberReader lr = new LineNumberReader(new BufferedReader(new FileReader("/tmp/test.txt")));
            List<String> lines = new LinkedList<>();
            for (String line = lr.readLine(); line != null; line = lr.readLine()) {
                lines.add(lr.getLineNumber() + " " + line);
            }
            assertThat(lines, equalTo(List.of(
                    "1 富贵不能淫", "2 贫贱不能移", "3 威武不能屈", "4 此之谓大丈夫"
            )));
        }
    }

    @Test
    public void testPipedStream() throws IOException {
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
    }

    @Test
    public void testPipedReaderWriter() throws IOException {
        ExecutorService es = Executors.newCachedThreadPool();
        PipedReader pr = new PipedReader();
        PipedWriter pw = new PipedWriter();
        pr.connect(pw);

        es.execute(() -> {
            try {
                BufferedWriter bw = new BufferedWriter(pw);
                bw.write("活着就是为了改变世界，难道还有其他原因吗");
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        es.execute(() -> {
            try {
                BufferedReader br = new BufferedReader(pr);
                assertEquals(br.readLine(), "活着就是为了改变世界，难道还有其他原因吗");
                br.close();
            } catch (Exception e) {
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
    }

    @Test
    public void testScanner() throws IOException {
        Scanner scanner = new Scanner(Paths.get("/tmp/test.txt"), "utf-8");
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
        scanner.close();
    }

    @Test
    public void testFiles() {
        {
            File file = new File("/tmp/test.txt");
            assertFalse(file.isDirectory());
            assertTrue(file.isFile());
            assertTrue(file.exists());
            assertEquals(file.getName(), "test.txt");
            assertEquals(file.getAbsolutePath(), "/tmp/test.txt");
            assertEquals(file.getParent(), "/tmp");
            assertEquals(file.getPath(), "/tmp/test.txt");
        }
        {
            System.out.println(System.getProperty("user.dir"));

            File directory = new File(".");
            for (File file : directory.listFiles()) {
                System.out.println(file.getName());
            }
        }
    }
}
