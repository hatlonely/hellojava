package util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

//import static org.junit.Assert.*;


class Point implements Serializable {
    private static final long serialVersionUID = 1420672609912364160L;
    private final int y;
    int x;

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
    public void testDataStream() throws IOException {
        {
            DataOutputStream dout = new DataOutputStream(new FileOutputStream("/tmp/test.txt"));
            dout.writeInt(10);
            dout.writeUTF("Rome wasn’t built in one day");
            dout.close();
        }
        {
            DataInputStream din = new DataInputStream(new FileInputStream("/tmp/test.txt"));
            assertEquals(din.readInt(), 10);
            assertEquals(din.readUTF(), "Rome wasn’t built in one day");
            din.close();
        }
    }

    @Test
    public void testObjectStream() throws IOException {
        {
            ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream("/tmp/test.txt"));
            oout.writeInt(10);
            oout.writeUTF("Nothing is impossible to a willing heart");
            oout.close();
        }
        {
            ObjectInputStream oin = new ObjectInputStream(new FileInputStream("/tmp/test.txt"));
            assertEquals(oin.readInt(), 10);
            assertEquals(oin.readUTF(), "Nothing is impossible to a willing heart");
            oin.close();
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
        // 查过 buffer 的大小，抛出 IOException
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
        assertThrows(IOException.class, () -> pr.unread("01234567890".toCharArray()));
    }

    @Test
    public void testFileReaderWriter() throws IOException {
        {
            FileWriter fw = new FileWriter("/tmp/test2.txt");
            assertEquals(fw.getEncoding(), "UTF8");
            System.out.println(fw.getEncoding());
            fw.write("初学者的心态；拥有初学者的心态是件了不起的事情");
            fw.flush();
            fw.close();
        }
        {
            FileReader fr = new FileReader("/tmp/test2.txt");
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
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream("/tmp/test1.txt"), "utf-8");
            ow.write("你究竟是想一辈子卖糖水，还是希望获得改变世界的机遇");
            ow.flush();
            ow.close();
        }
        {
            InputStreamReader rw = new InputStreamReader(new FileInputStream("/tmp/test1.txt"), "utf-8");
            StringBuilder sb = new StringBuilder();
            for (int ch = rw.read(); ch != -1; ch = rw.read()) {
                sb.append((char) ch);
            }
            assertEquals(sb.toString(), "你究竟是想一辈子卖糖水，还是希望获得改变世界的机遇");
            rw.close();
        }
    }


    @Test
    public void testFile1() {
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

    @Test
    public void testFileReaderWriter1() throws Exception {
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter("/tmp/test.txt"));
            bw.write("The Universe in a Nutshell");
            bw.newLine();
            bw.write("A Brief History of Time");
            bw.newLine();
            bw.close();
        }
        {
            BufferedReader br = new BufferedReader(new FileReader("/tmp/test.txt"));
            assertEquals(br.readLine(), "The Universe in a Nutshell");
            assertEquals(br.readLine(), "A Brief History of Time");
            assertEquals(br.readLine(), null);
            br.close();
        }
        {
            BufferedWriter bw = Files.newBufferedWriter(Paths.get("/tmp/test.txt"), StandardCharsets.UTF_8);
            bw.write("果壳里的宇宙");
            bw.newLine();
            bw.write("时间简史");
            bw.newLine();
            bw.close();
        }
        {
            BufferedReader br = Files.newBufferedReader(Paths.get("/tmp/test.txt"), StandardCharsets.UTF_8);
            assertEquals(br.readLine(), "果壳里的宇宙");
            assertEquals(br.readLine(), "时间简史");
            assertEquals(br.readLine(), null);
        }
        {
            Scanner scanner = new Scanner(Paths.get("/tmp/test.txt"), "utf-8");
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
            scanner.close();
        }
    }

    @Test
    public void testString() throws IOException {
        StringWriter writer = new StringWriter();
        writer.write("hello");
        writer.write(" ");
        writer.write("world");
        writer.write("\n");
        StringReader reader = new StringReader(writer.getBuffer().toString());
        char[] buf = new char[11];
        assertEquals(reader.read(buf), "hello world".length());
        assertEquals(new String(buf), "hello world");
    }

    @Test
    public void testPiped() throws IOException {
        {
            PipedInputStream pis = new PipedInputStream();
            PipedOutputStream pos = new PipedOutputStream();
            pis.connect(pos);
            pos.write("hello world".getBytes());
            byte[] buf = new byte[11];
            assertEquals(pis.read(buf), "hello world".length());
            assertEquals(new String(buf), "hello world");
            pos.close();
            pis.close();
        }
        {
            PipedWriter pw = new PipedWriter();
            PipedReader pr = new PipedReader();
            pw.connect(pr);
            pw.write("hello world");
            char[] buf = new char[11];
            assertEquals(pr.read(buf), "hello world".length());
            assertEquals(new String(buf), "hello world");
            pw.close();
            pr.close();
        }
    }

    @Test
    public void testLineNumberInputStream() throws IOException {
        LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader("/tmp/test.txt")));

        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            System.out.println(reader.getLineNumber() + " " + line);
        }
    }
}
