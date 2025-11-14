package ui;

import java.util.Scanner;

/**
 * Text based user interface for interacting, acts
 * as a frontend for the control panel.
 */
public class TextBasedUi {

  /**
   * Starts the text based user interface.
   */
  public void start() {
    try {
      boolean running = true;
      while (running) {
        displayMenu();
        int choice = getUserChoice();

        switch (choice) {
          case 1:
            // list available greenhouses
            break;

          case 2:
            // connect to a greenhouse
            break;

          case 3:
            // exit the program
            running = false;
            break;

          default:
            System.out.println("Invalid choice. Please try again.");
        }
      }
    } catch (Exception e) {
      System.out.println("An error occurred:");
      System.out.println(e.getMessage());
    }
  }

  /**
   * Displays the main menu.
   */
  private void displayMenu() {
    System.out.println("===============================");
    System.out.println("=== SMART GREENHOUSE CLIENT ===");
    System.out.println("===============================");
    System.out.println("1. List Available Greenhouses");
    System.out.println("2. Connect to a Greenhouse");
    System.out.println("3. Exit");
    System.out.print("Enter your choice: ");
  }

  /**
   * Method for getting the user's
   * choice from the menu.
   *
   * @return the user's choice, as an integer
   */
  private int getUserChoice() {
    Scanner input = new Scanner(System.in);
    try {
      int choice = input.nextInt();
      input.nextLine();
      return choice;
    } catch (Exception e) {
      System.out.println("Invalid choice. Please try again.");
    }

    return 0;
  }
}