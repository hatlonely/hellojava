package socket;

import java.io.*;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 6789);

        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        oos.writeUTF("hello world 你好世界");
        oos.flush();
        socket.shutdownOutput();

        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        System.out.println("server [" + socket.getRemoteSocketAddress() + "]: " + ois.readUTF());
        socket.shutdownInput();

        ois.close();
        oos.close();
        socket.close();
    }
}
