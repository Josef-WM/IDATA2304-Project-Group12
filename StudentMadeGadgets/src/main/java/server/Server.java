package server;

import greenhouse.GreenhouseRegistry;
import protocol.Protocol;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Javadoc placeholder.
 */
public class Server {

  private static GreenhouseRegistry greenhouseRegistry = new GreenhouseRegistry();

  /**
   * Force start a server
   */
  public static void main(String[] args) throws IOException {
    runServer();
  }

  /**
   * Starts a new server on port 6767
   */
  public static void runServer() throws IOException {
    try (ServerSocket serverSocket = new ServerSocket(6767)) {
      System.out.println("Server is now listening on 6767");

      while (true) {
        Socket socket = serverSocket.accept();
        new Thread(() -> handle(socket)).start();
      }
    }
  }

  /**
   * Javadoc placeholder.
   */
  public static void handle(Socket socket) {
    try (Protocol protocol = new Protocol(socket)) {
      System.out.println("A client has connected: " + socket.getRemoteSocketAddress());

      String message;
      while ((message = protocol.readMessage()) != null) {
        message = message.trim();
        System.out.println("Got: " + message);

        if (message.equals("EXIT")) {
          protocol.sendMessage("Goodbye");
          System.out.println("A client has disconnected: " + socket.getRemoteSocketAddress());
          break;
        }
      }
    } catch (IOException e) {
      System.out.println("Client error: " + e.getMessage());
    }
  }
}