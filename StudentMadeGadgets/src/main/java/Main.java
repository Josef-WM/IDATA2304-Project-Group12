import server.Server;
import client.ControlPanelNode;

import java.io.IOException;
import java.util.Scanner;

/**
 * Javadoc placeholder.
 */
public class Main {

  /**
   * Javadoc placeholder.
   */
  public static void main(String[] args) throws IOException {
    System.out.println("Welcome to SMG");
    System.out.println("To set up a server use RUN server");
    System.out.println("To enter a Control Panel use RUN cpanel");
    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine();

    if (input.startsWith("RUN")) {
      if (input.contains("server")) {
        Server.runServer();
      }
      else if (input.contains("cpanel")) {
        ControlPanelNode.runControlPanel();
      }
    }
  }
}
