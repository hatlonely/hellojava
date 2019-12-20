package javase;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DateTimeTest {
    @Test
    public void testTimestamp() {
        System.out.println(new Date().getTime());
        System.out.println(Instant.now().getEpochSecond());
        System.out.println(new Date(1521121451L * 1000));
    }

    @Test
    public void testDateFormat() throws Exception {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Date d = df.parse("2019-12-20 19:09:59");
        assertEquals(df.format(d), "2019-12-20 19:09:59");
    }

    @Test
    public void testDate() throws Exception {
        for (final Date d : Arrays.asList(
                new Date(),
                new Date(1576840199L * 1000),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-12-20 19:09:59"),
                new GregorianCalendar(2019, Calendar.DECEMBER, 20, 19, 9, 59).getTime()
        )) {
            System.out.println(d);
            d.setTime(1576840199L * 1000);

            assertEquals(d.getTime(), 1576840199L * 1000);
            assertTrue(d.before(new Date()));
            assertTrue(new Date().after(d));
        }
    }

    @Test
    public void testInstant() {
        for (final Instant i : Arrays.asList(
                Instant.parse("2019-12-20T19:09:59Z"),
                Instant.now(),
                new Date().toInstant()
        )) {
            System.out.println(i);
            System.out.println(i.getEpochSecond());
            System.out.println(i.minusSeconds(3600));
            System.out.println(i.plusSeconds(3600));
        }
    }

    @Test
    public void testCalendar() {
        final Calendar c = Calendar.getInstance();
        c.set(2019, Calendar.DECEMBER, 20, 19, 9, 59);
        System.out.println(c);
        c.add(Calendar.DATE, 2);
        c.add(Calendar.HOUR, 2);
        System.out.println(c);
    }
}
