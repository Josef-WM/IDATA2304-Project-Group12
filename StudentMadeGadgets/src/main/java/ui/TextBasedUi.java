package ui;

import client.ControlPanelNode;
import java.util.Scanner;

/**
 * Text based user interface for interacting, acts
 * as a frontend for the control panel.
 */
public class TextBasedUi {

  Scanner scanner = new Scanner(System.in);
  ControlPanelNode activeControlPanel = new ControlPanelNode("Localhost", 6767);
  /**
   * Starts the text based user interface.
   */
  public void start() {
    try {
      boolean running = true;
      while (running) {
        if (!activeControlPanel.isConnected()) {
          displayServerConnectionMenu();
          int choice = getUserChoice();
          switch (choice) {
            case 1:
              // connect to a server
              connectToServer();
              break;
            case 2:
              // exit the program
              System.out.println("Goodbye!");
              running = false;
              break;

            default:
              System.out.println("Invalid choice. Please try again.");
          }
        } else {
          displayControlPanelMainPage();
          int choice = getUserChoice();
          switch (choice) {
            case 1:
              // TODO: List all Greenhouses on the server that
              //  'activeControlPanel' is connected to
              System.out.println("Work in progress! :)");
              break;
            case 2:
              activeControlPanel.disconnect();
              activeControlPanel = null;
              System.out.println("You have been disconnected from the server");
              running = false;
              break;
            default:
              System.out.println("Invalid choice. Please try again.");
          }
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

  /**
   * Displays the main control panel page
   */
  private void displayControlPanelMainPage() {
    displayHeader();
    System.out.println("1. Lists all Greenhouses on the server");
    System.out.println("2. Disconnect from server");
    System.out.print("Enter your choice: ");
  }

  // Connects your control panel to a server
  private void connectToServer() {
    System.out.println("Enter a Server host's IP:");
    try {
      String hostIP = scanner.nextLine();
      activeControlPanel.setHost(hostIP);
      activeControlPanel.connect();
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
    try {
      int choice = scanner.nextInt();
      scanner.nextLine();
      return choice;
    } catch (Exception e) {
      System.out.println("Invalid choice. Please try again.");
    }

    return 0;
  }
}