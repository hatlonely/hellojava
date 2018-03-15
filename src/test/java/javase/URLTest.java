package buildin;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.junit.Test;

public class URLTest {
    @Test
    public void testURL() {
        try {
            URL url = new URL("https://leetcode.com/problemset/all/?search=tow");
            assertEquals(url.getProtocol(), "https");
            assertEquals(url.getHost(), "leetcode.com");
            assertEquals(url.getPath(), "/problemset/all/");
            assertEquals(url.getQuery(), "search=tow");

            String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36";
            String userAgentEncode = "Mozilla%2F5.0+%28Macintosh%3B+Intel+Mac+OS+X+10_12_3%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F65.0.3325.162+Safari%2F537.36";

            assertEquals(URLEncoder.encode(userAgent, "utf-8"), userAgentEncode);
            assertEquals(URLDecoder.decode(userAgentEncode, "utf-8"), userAgent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
