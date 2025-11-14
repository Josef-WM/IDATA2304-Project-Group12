package ui;

import client.ControlPanelNode;

import java.io.IOException;
import java.util.Scanner;

/**
 * Text based user interface for interacting, acts
 * as a frontend for the control panel.
 */
public class TextBasedUi {

  Scanner scanner = new Scanner(System.in);
  /**
   * Starts the text based user interface.
   */
  public void start() {
    try {
      boolean running = true;
      while (running) {
        displayServerConnectionMenu();
        int choice = getUserChoice();

        switch (choice) {
          case 1:
            // connect to a server
            connectToServer();
            break;
          case 2:
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
   * Displays application header
   */
  public void displayHeader() {
    System.out.println("===============================");
    System.out.println("=== SMART GREENHOUSE CLIENT ===");
    System.out.println("===============================");
  }

  /**
   * Displays server connection menu.
   */
  private void displayServerConnectionMenu() {
    displayHeader();
    System.out.println("1. Connect to a server");
    System.out.println("2. Exit");
    System.out.print("Enter your choice: ");
  }

  // Connects and creates a Control Panel to a specified server.
  private void connectToServer() throws IOException {
    System.out.println("Enter a Server host's IP:");
    try {
      String hostIP = scanner.nextLine();
      ControlPanelNode controlPanelNode = new ControlPanelNode(hostIP, 6767);
    } catch (Exception e) {
      System.out.println("Failed to connect to server. " + e.getMessage());
    }
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