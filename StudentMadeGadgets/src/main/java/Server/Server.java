package Server;

import Protocol.Protocol;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public static void main(String[] args) throws IOException {
    try (ServerSocket serverSocket = new ServerSocket(6767)) {
      System.out.println("Server is now listening on 6767");
      while (true) {
        Socket socket = serverSocket.accept();
        new Thread(() -> handle(socket)).start();
      }
    }
  }

  public static void handle(Socket socket) {
    try (Protocol protocol = new Protocol(socket)) {
      System.out.println("A client has connected: " + socket.getRemoteSocketAddress());
      String message = protocol.readMessage();
      if (message != null) {
        System.out.println("Got: " + message);
        protocol.sendMessage("ACK: " + message);
      }
    } catch (IOException e) {
      System.out.println("Client error: " + e.getMessage());
    }
  }
}