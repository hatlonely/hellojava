package javase;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamTest {
    @Test
    public void testInputStream() throws IOException {
        InputStream fis = new FileInputStream("/tmp/test.txt");
        fis.close();
    }
}
