package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public static void main(String[] args) throws IOException {
    // creating a server socket on port 6767
    ServerSocket serverSocket = new ServerSocket(6767);
    System.out.println("Server listening");
    while (true) {
      Socket clientSocket = serverSocket.accept();
      System.out.println("Server -> Client " + clientSocket.toString() + " has connected");
    }
  }
}