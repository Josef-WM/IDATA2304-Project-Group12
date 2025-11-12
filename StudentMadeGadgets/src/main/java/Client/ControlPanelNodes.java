package Client;

import Protocol.Protocol;
import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;

public class ControlPanelNodes {
  public static void main(String args[]) throws IOException {
    try (Socket socket = new Socket("Localhost", 6767);
         Protocol protocol = new Protocol(socket)){

      System.out.println("You connected to the server");
      System.out.println("Send a message to the server:");
      Scanner scanner = new Scanner(System.in);
      String input = scanner.nextLine();
      protocol.sendMessage(input);
      String reply = protocol.readMessage();
      System.out.println("Server says: " + reply);
    } catch (IOException e) {
      System.out.println("Failed to connect to the server " + e.getMessage());
    }
  }
}
