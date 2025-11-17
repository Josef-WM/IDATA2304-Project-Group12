package ui;

import client.ControlPanelNode;
import protocol.command.GreenhouseListData;
import protocol.command.Information;

import java.io.IOException;
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
      while (true) {
        if (!activeControlPanel.isConnected()) {
          serverConnectionMenu();
        } else {
          displayControlPanelMainPage();
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
  public void displayHeader(String title) {
    System.out.println("===============================");
    System.out.println("=== " + title + " ===");
    System.out.println("===============================");
  }

  /**
   * Displays server connection menu.
   */
  private void serverConnectionMenu() {
    displayHeader("SMART GREENHOUSE CLIENT");
    System.out.println("1. Connect to a server");
    System.out.println("2. Exit");
    int choice = getUserChoice("Enter choice: ");

    switch (choice) {
      case 1 -> connectToServer();
      case 2 -> exit();
      default -> System.out.println("Invalid choice!");
    }
  }

  private void exit() {
    System.out.println("Goodbye!");
    System.exit(0);
  }

  /**
   * Displays the main control panel page
   */
  private void displayControlPanelMainPage() throws IOException {
    displayHeader("SMART GREENHOUSE CLIENT");
    System.out.println("1. Lists all Greenhouses on the server");
    System.out.println("2. Select a greenhouse");
    System.out.println("3. Create a greenhouse");
    System.out.println("4. Disconnect from server");
    int choice = getUserChoice("Enter choice: ");

    switch (choice) {
      case 1 -> {
        GreenhouseListData greenhouseListData = activeControlPanel.getAllGreenhouses();
        for (int i = 0; i < greenhouseListData.getGreenhouses().size(); i++) {
          System.out.println(i+1 + ". " + greenhouseListData.getGreenhouses().get(i).getGreenhouseName());
        }
      }
      case 2 -> System.out.println("Work in progress :'(");
      case 3 -> createGreenhouseMenu();
      case 4 -> disconnect();
      default -> System.out.println("Invalid choice!");
    }
  }

  public void createGreenhouseMenu() throws IOException {
    System.out.println("Choose greenhouse name: ");
    String name = scanner.nextLine();

    Information information = activeControlPanel.createGreenhouse(name);

    System.out.println(information.getInformation());
  }

  private void disconnect() {
    try {
      activeControlPanel.disconnect();
    } catch (IOException e) {
      System.out.println("Error while disconnecting control panel");
    }
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
  private int getUserChoice(String prompt) {
    System.out.println(prompt);
    while (!scanner.hasNextInt()) {
      System.out.println("Invalid input. " + prompt);
      scanner.next();
    }
    int value = scanner.nextInt();
    scanner.nextLine();
    return value;
  }
}