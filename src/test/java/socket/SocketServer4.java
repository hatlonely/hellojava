package socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

public class SocketServer4 {
    public static void main(String[] args) throws IOException, InterruptedException {
        CharsetDecoder utf8 = Charset.forName("utf-8").newDecoder();

        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(6789));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, serverChannel.validOps(), null);

        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (key.isAcceptable()) {
                    SocketChannel channel = serverChannel.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer bb = ByteBuffer.allocate(3);
                    CharBuffer cb = CharBuffer.allocate(3);
                    StringBuilder sb = new StringBuilder();
                    for (int i = channel.read(bb); i != -1; i = channel.read(bb)) {
                        bb.flip();
                        utf8.decode(bb, cb, true);
                        cb.flip();
                        while (cb.hasRemaining()) {
                            sb.append(cb.get());
                        }
                        bb.compact();
                        cb.clear();
                    }
                    key.attach(sb.toString());
                    key.interestOps(SelectionKey.OP_WRITE);
                    channel.shutdownInput();
                } else if (key.isWritable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    System.out.println("client [" + channel.getRemoteAddress() + "]: " + key.attachment());
                    ByteBuffer bb = ByteBuffer.wrap("ok".getBytes());
                    channel.write(bb);
                    channel.shutdownOutput();
                    channel.close();
                }
                it.remove();
            }
        }
    }
}
