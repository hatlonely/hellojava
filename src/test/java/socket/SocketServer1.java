package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer1 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6789);

        while (true) {
            Socket socket = serverSocket.accept();

            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            System.out.println("client [" + socket.getRemoteSocketAddress() + "]: " + ois.readUTF());
            socket.shutdownInput();

            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            oos.writeUTF("ok");
            oos.flush();
            socket.shutdownOutput();

            ois.close();
            oos.close();
            socket.close();
        }
    }
}
