package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
  public static void main(String[] args) throws IOException {
    // creating a server socket on port 6767
    ServerSocket serverSocket = new ServerSocket(6767);

    // close the server socket
    serverSocket.close();
  }
}
