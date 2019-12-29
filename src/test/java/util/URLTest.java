package util;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;

import static org.junit.Assert.assertEquals;

public class URLTest {
    @Test
    public void testURL() {
        try {
            URL url = new URL("https://leetcode.com/problemset/all/?search=tow");
            assertEquals(url.getProtocol(), "https");
            assertEquals(url.getHost(), "leetcode.com");
            assertEquals(url.getPath(), "/problemset/all/");
            assertEquals(url.getQuery(), "search=tow");
            assertEquals(url.getFile(), "/problemset/all/?search=tow");
            assertEquals(url.getDefaultPort(), 443);

            String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36";
            String userAgentEncode = "Mozilla%2F5.0+%28Macintosh%3B+Intel+Mac+OS+X+10_12_3%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F65.0.3325.162+Safari%2F537.36";

            assertEquals(URLEncoder.encode(userAgent, "utf-8"), userAgentEncode);
            assertEquals(URLDecoder.decode(userAgentEncode, "utf-8"), userAgent);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRequest() {
        try {
            URL url = new URL("https://leetcode.com/problemset/all/?search=tow");
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(500);
            connection.setReadTimeout(500);
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36");
            connection.connect();
            // header
            connection.getHeaderFields().forEach((key, val) -> System.out.println(key + " => " + val));
            // body
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            reader.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testURI() {
        try {
            URI uri = new URI("mailto:java-net@java.sun.com");
            assertEquals(uri.getScheme(), "mailto");
            assertEquals(uri.getSchemeSpecificPart(), "java-net@java.sun.com");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
