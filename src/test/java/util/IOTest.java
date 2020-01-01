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
import java.util.Scanner;
import java.util.Vector;

import static org.junit.Assert.*;


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
    public void testFile() {
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
    public void testFileReaderWriter() throws Exception {
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
    public void testFileStream() throws Exception {
        {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new BufferedOutputStream(new FileOutputStream("/tmp/test.bin")));
            oos.writeInt(123456789);
            oos.writeObject("hello world");
            oos.writeObject(new Point(3, 4));
            oos.close();
        }
        {
            ObjectInputStream ois = new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream("/tmp/test.bin")));
            assertEquals(ois.readInt(), 123456789);
            assertEquals(ois.readObject(), "hello world");
            Point point = (Point) ois.readObject();
            assertEquals(point.x, 3);
            assertEquals(point.y, 4);
            ois.close();
        }
    }

    @Test
    public void testCharArray() throws IOException {
        {
            ByteArrayOutputStream aos = new ByteArrayOutputStream();
            aos.write("hello world".getBytes());
            ByteArrayInputStream ais = new ByteArrayInputStream(aos.toByteArray());
            assertEquals("hello world", new String(ais.readAllBytes()));
        }
        {
            CharArrayWriter writer = new CharArrayWriter();
            writer.write("hello world");
            CharArrayReader reader = new CharArrayReader(writer.toCharArray());
            char[] buf = new char[11];
            assertEquals(reader.read(buf), "hello world".length());
            assertEquals(new String(buf), "hello world");
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

    @Test
    public void testPushBack() throws IOException {
        {
            PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream("hello world".getBytes()));
            byte[] buf = new byte[11];
            assertEquals(pis.read(buf, 0, 11), 11);
            assertEquals(new String(buf), "hello world");
            pis.unread('b');
            assertEquals(pis.read(), 'b');
        }
        {
            PushbackReader pr = new PushbackReader(new CharArrayReader("hello world".toCharArray()));
            char[] buf = new char[11];
            assertEquals(pr.read(buf, 0, 11), 11);
            assertEquals(new String(buf), "hello world");
            pr.unread('b');
            assertEquals(pr.read(), 'b');
        }
    }

    @Test
    public void testSequence() throws IOException {
        InputStream is1 = new ByteArrayInputStream("abcdefghijklmnopqrstuvwxyz".getBytes());
        InputStream is2 = new ByteArrayInputStream("0123456789".getBytes());

        Vector<InputStream> vi = new Vector<>();
        vi.add(is1);
        vi.add(is2);
        SequenceInputStream ss = new SequenceInputStream(vi.elements());

        for (int b = ss.read(); b != -1; b = ss.read()) {
            System.out.println((char) b);
        }
    }
}
