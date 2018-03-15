package javase;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateTimeTest {
    @Test
    public void testDateAdd() {
        {
            Calendar calendar = Calendar.getInstance();
            Date d1 = new Date();
            calendar.setTime(d1);
            calendar.add(Calendar.DATE, 1);
            Date d2 = calendar.getTime();
            System.out.println(d1);
            System.out.println(d2);
        }
        {
            Date d1 = new Date();
            LocalDateTime localDateTime = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            Date d2 = Date.from(localDateTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
            System.out.println(d1);
            System.out.println(d2);
        }
    }

    @Test
    public void testTimestamp() {
        System.out.println(new Date().getTime());
        System.out.println(Instant.now().getEpochSecond());
        System.out.println(new Date(1521121451L * 1000));
    }

    @Test
    public void testDateFormat() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(date));

        try {
            System.out.println(format.parse("2018-03-15 21:50:43"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}