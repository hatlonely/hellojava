package util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FilesTest {
    private static void createFile() throws IOException {
        FilesTest.deleteFile();
        BufferedWriter bw = Files.newBufferedWriter(Paths.get("/tmp/test1.txt"));
        bw.write("hello world");
        bw.newLine();
        bw.close();
    }

    private static void deleteFile() throws IOException {
        Files.deleteIfExists(Paths.get("/tmp/test1.txt"));
        Files.deleteIfExists(Paths.get("/tmp/test2.txt"));
        Files.deleteIfExists(Paths.get("/tmp/test3.txt"));
    }

    @Before
    public void setUp() throws IOException {
        FilesTest.createFile();
    }

    @After
    public void tearDown() throws IOException {
        FilesTest.deleteFile();
    }

    @Test
    public void testFiles() throws Exception {
        assertTrue(Files.exists(Paths.get("/tmp/test1.txt")));
        {
            Files.copy(Paths.get("/tmp/test1.txt"), Paths.get("/tmp/test2.txt"));
            byte[] bs = Files.readAllBytes(Paths.get("/tmp/test2.txt"));
            assertEquals("hello world\n", new String(bs));
        }
        {
            Files.move(Paths.get("/tmp/test2.txt"), Paths.get("/tmp/test3.txt"));
        }
    }

    @Test
    public void testDirectory() throws Exception {
        {
            File directory = new File("/tmp");
            assertTrue(directory.isDirectory());

            for (File f : directory.listFiles()) {
                System.out.println(f.getName());
            }
        }
        {
            for (Path p : Files.newDirectoryStream(Paths.get("/tmp"), "*.txt")) {
                System.out.println(p.getFileName());
            }
        }
    }
}
