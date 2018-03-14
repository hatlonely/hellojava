package buildin;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.junit.Test;

public class StreamTest {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}