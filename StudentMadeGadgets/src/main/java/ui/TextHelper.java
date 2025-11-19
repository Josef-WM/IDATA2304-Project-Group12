package ui;

import java.util.Scanner;

/**
 * Class with a few helpful methods to be used
 * with the text based user interface.
 */
public class TextHelper {

  Scanner scanner = new Scanner(System.in);

  /**
   * Clears the screen, works on multiple operating systems.
   */
  public void clearScreen() {
    try {
      String os = System.getProperty("os.name").toLowerCase();
      if (os.contains("windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else if (os.contains("linux") || os.contains("mac")) {
        new ProcessBuilder("clear").inheritIO().start().waitFor();
      } else {
        System.out.print("\033[H\033[2J");
        System.out.flush();
      }
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Method for printing out coloured text.
   *
   * @param text text to print
   * @param colour colour to use. Can be either black, red,
   *        green, yellow, blue, magenta, cyan, or white.
   */
  public void printTextWithColour(String text, String colour) {

    switch (colour) {

      case "black":
        System.out.print("\033[30m");
        break;

      case "red":
        System.out.print("\033[31m");
        break;

      case "green":
        System.out.print("\033[32m");
        break;

      case "yellow":
        System.out.print("\033[33m");
        break;

      case "blue":
        System.out.print("\033[34m");
        break;

      case "magenta":
        System.out.print("\033[35m");
        break;

      case "cyan":
        System.out.print("\033[36");
        break;

      case "white":
        System.out.print("\033[37m");
        break;

      default:
    }
    System.out.print(text);
    System.out.print("\033[0m");
  }

  /**
   * Displays application header.
   */
  public void displayHeader(String title, String colour) {
    clearScreen();
    int len = title.length();

    // top line
    for (int i = 0; i < len; i++) {
      System.out.print("=");
    }

    System.out.println();
    printTextWithColour(title, colour);
    System.out.println();

    for (int i = 0; i < len; i++) {
      System.out.print("=");
    }
    System.out.println();
  }

  /**
   * Method to wait for user interaction, usually
   * invoked before clearing the screen.
   */
  public void pressEnterToContinue() {
    printTextWithColour("Press any key to continue...", "yellow");
    scanner.nextLine();
  }

  /**
   * Method for getting the user's
   * choice from the menu.
   *
   * @return the user's choice, as an integer
   */
  public int getUserChoice(String prompt, int maxChoice) {
    printTextWithColour(prompt, "blue");

    while (true) {
      String input = scanner.nextLine();

      try {
        int value = Integer.parseInt(input);

        if (value >= 0 && value <= maxChoice) {
          return value;
        }

        printTextWithColour("Invalid input. " + prompt, "red");
      } catch (NumberFormatException e) {
        printTextWithColour("Invalid input. " + prompt, "red");
      }
    }
  }
}
