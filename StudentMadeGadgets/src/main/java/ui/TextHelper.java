package ui;

/**
 * Class with a few helpful methods to be used
 * with the text based user interface.
 */
public class TextHelper {

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
   * @param colour colour to use. Can be either red, green, yellow, blue, magenta, or cyan.
   */
  public void printTextWithColour(String text, String colour) {

    switch (colour) {
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

      default:
    }
    System.out.println(text);
  }

  /**
   * Displays application header.
   */
  public void displayHeader(String title) {
    clearScreen();
    int len = title.length();

    // top line
    for (int i = 0; i < len; i++) {
      System.out.print("=");
    }
    System.out.println();

    System.out.println(title);

    for (int i = 0; i < len; i++) {
      System.out.print("=");
    }
    System.out.println();
  }
}
