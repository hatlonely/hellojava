package javase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.junit.Test;

class Point implements Serializable {
    private static final long serialVersionUID = 1420672609912364160L;

    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class StreamTest {
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
    public void testReaderWriter() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIoStream() {
        try {
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
                assertEquals((String) ois.readObject(), "hello world");
                Point point = (Point) ois.readObject();
                assertEquals(point.x, 3);
                assertEquals(point.y, 4);
                ois.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}