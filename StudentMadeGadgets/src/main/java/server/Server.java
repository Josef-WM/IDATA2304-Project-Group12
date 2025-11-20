package server;

import greenhouse.GreenhouseRegistry;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import protocol.CommandHandler;
import protocol.Protocol;

/**
 * Javadoc placeholder.
 */
public class Server {

  private static final GreenhouseRegistry greenhouseRegistry = new GreenhouseRegistry();
  private static CommandHandler commandHandler;

  /**
   * Force start a server.
   */
  public static void main(String[] args) throws IOException {
    runServer();
  }

  /**
   * Starts a new server on port 6767.
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
   * Handles a client connection.
   *
   * @param socket the client socket
   */
  public static void handle(Socket socket) {
    try (Protocol protocol = new Protocol(socket)) {
      System.out.println("A client has connected: " + socket.getRemoteSocketAddress());

      String message;
      while ((message = protocol.readMessage()) != null) {
        message = message.trim();
        System.out.println(message);
        String reply = commandHandler.handleCommand(message);
        System.out.println(reply);
        protocol.sendMessage(reply);
      }
    } catch (IOException e) {
      System.out.println("Client error: " + e.getMessage());
    }
  }

  /**
   * Gets the greenhouse registry.
   *
   * @return the greenhouse registry
   */
  public static GreenhouseRegistry getGreenhouseRegistry() {
    return greenhouseRegistry;
  }
}