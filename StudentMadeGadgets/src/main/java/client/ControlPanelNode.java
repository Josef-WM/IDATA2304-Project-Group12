package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import protocol.Protocol;

/**
 * ControlPanelNode class, includes methods for connecting
 * to and sending commands to the server.
 */
public class ControlPanelNode {
  String host = "localhost";
  int port = 6767;

  /**
   * Constructor for the ControlPanelNode class.
   *
   * @param host the host/ip of the server to connect to, defaults to localhost
   * @param port the port number to use, defaults to 6767 per the protocol specifications
   */
  public ControlPanelNode(String host, int port) {
    this.host = host;
    this.port = port;
  }

  /**
   * Connects to the server, specified with the host and port.
   */
  public void connect() throws IOException {
    try (Socket socket = new Socket(host, port);
         Protocol protocol = new Protocol(socket)) {

      System.out.println("Connected to " + host + ":" + port);

      System.out.println("Commands:");
      System.out.println("  READ temp");
      System.out.println("  TOGGLE fan");
      System.out.println("  EXIT");
      System.out.println();

      Scanner scanner = new Scanner(System.in);

      while (true) {
        System.out.print("> ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("EXIT")) {
          protocol.sendMessage("EXIT");
          break;
        }

        // send the command to the server
        protocol.sendMessage(input);

        // print server reply
        String reply = protocol.readMessage();
        if (reply == null) {
          System.out.println("Server closed the connection.");
          break;
        }
        System.out.println(reply);
      }
    } catch (IOException e) {
      System.out.println("Failed to connect to the server " + e.getMessage());
    }
  }
}
