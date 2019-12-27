package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer3 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6789);

        ExecutorService es = Executors.newFixedThreadPool(4);
        while (true) {
            Socket socket = serverSocket.accept();

            es.submit(() -> {
                try {
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
