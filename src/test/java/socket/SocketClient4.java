package socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class SocketClient4 {
    public static void main(String[] args) throws IOException {
        CharsetEncoder utf8 = Charset.forName("utf-8").newEncoder();

        SocketChannel channel = SocketChannel.open(new InetSocketAddress(6789));
        channel.configureBlocking(false);

        // write
        CharBuffer cb = CharBuffer.wrap("hello world 你好世界".toCharArray());
        ByteBuffer bb = ByteBuffer.allocate(3);
        while (cb.hasRemaining()) {
            utf8.encode(cb, bb, true);
            bb.flip();
            channel.write(bb);
            bb.clear();
        }
        channel.shutdownOutput();

        // read
        for (int i = channel.read(bb); i != -1; i = channel.read(bb)) {
            bb.flip();
            while (bb.hasRemaining()) {
                System.out.print((char) bb.get());
            }
            bb.clear();
        }
        channel.shutdownInput();

        channel.close();
    }
}
