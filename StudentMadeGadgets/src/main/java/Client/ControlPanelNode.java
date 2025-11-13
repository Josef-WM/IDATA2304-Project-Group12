package Client;

import Protocol.Protocol;
import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;

public class ControlPanelNode {

  public static void main(String[] args) {
    runControlPanel();
  }

  public static void runControlPanel() {
    System.out.println("Enter IP of server host:");
    Scanner scanner = new Scanner(System.in);
    String hostID = scanner.nextLine();
    try (Socket socket = new Socket(hostID, 6767);
         Protocol protocol = new Protocol(socket);) {


      System.out.println("You connected to the server");
      System.out.println("Commands:");
      System.out.println("  READ temp");
      System.out.println("  TOGGLE fan");
      System.out.println("  exit");
      System.out.println();

      while (true) {
        System.out.print("> ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("exit")) {
          protocol.sendMessage("QUIT");
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
