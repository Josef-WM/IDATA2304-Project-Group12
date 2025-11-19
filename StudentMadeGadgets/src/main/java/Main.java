import java.io.IOException;
import java.util.Scanner;
import server.Server;
import ui.TextBasedUi;
import ui.TextHelper;

/**
 * Javadoc placeholder.
 */
public class Main {

  /**
   * Javadoc placeholder.
   */
  public static void main(String[] args) throws IOException {

    printHelp("");
    Scanner scanner = new Scanner(System.in);

    boolean running = true;
    while (running) {
      String input = scanner.nextLine();
      switch (input.toUpperCase()) {
        case "RUN SERVER" -> {
          Server.runServer();
          running = false;
        }
        case "RUN CPANEL" -> {
          TextBasedUi textBasedUi = new TextBasedUi();
          textBasedUi.start();
          running = false;
        }
        default -> {
          printHelp("Invalid input!");
        }
      }
    }
  }

  private static void printHelp(String extras) {
    TextHelper textHelper = new TextHelper();
    textHelper.clearScreen();
    System.out.println("Welcome to our Student Made Gadgets application!");
    System.out.print("To set up a Server use");
    textHelper.printTextWithColour(" RUN server", "green");
    System.out.println();
    System.out.print("To enter a Control Panel use");
    textHelper.printTextWithColour(" RUN cpanel", "blue");
    System.out.println();
    if (!extras.isEmpty()) {
      System.out.println(extras);
    }
  }
}
