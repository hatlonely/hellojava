package util;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class LoggingTest {
    @Test
    public void testLogging() {
        Logger infoLog = Logger.getLogger("info");
        infoLog.setLevel(Level.INFO);

        ConsoleHandler console = new ConsoleHandler();
        // 设置输出格式
        console.setFormatter(new java.util.logging.Formatter() {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            @Override
            public String format(LogRecord record) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                sb.append(record.getLevel());
                sb.append("] [");
                sb.append(df.format(new Date()));
                sb.append("] [");
                sb.append(record.getSourceClassName());
                sb.append(".");
                sb.append(record.getSourceMethodName());
                sb.append("] [");
                sb.append(Thread.currentThread().getName());
                sb.append("] ");
                sb.append(record.getMessage());
                sb.append("\n");
                return sb.toString();
            }
        });
        // 关闭默认输出
        infoLog.setUseParentHandlers(false);
        // 添加输出到默认终端
        infoLog.addHandler(console);

        // 过滤一半的日志
        infoLog.setFilter(new Filter() {
            int count = 0;

            @Override
            public boolean isLoggable(LogRecord record) {
                count++;
                return count % 2 != 0;
            }
        });

        infoLog.severe("hello world");
        infoLog.warning("hello world");
        infoLog.info("hello world");
        infoLog.fine("hello world");
        infoLog.config("hello world");
        infoLog.finer("hello world");
        infoLog.fine("hello world");
    }
}
