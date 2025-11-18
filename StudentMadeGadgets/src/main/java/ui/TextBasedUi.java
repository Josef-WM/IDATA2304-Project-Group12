package ui;

import client.ControlPanelNode;
import java.io.IOException;
import java.util.Scanner;
import protocol.command.GreenhouseListData;
import protocol.command.Information;

/**
 * Text based user interface for interacting, acts
 * as a frontend for the control panel.
 */
public class TextBasedUi {

  private boolean running = true;
  Scanner scanner = new Scanner(System.in);
  ControlPanelNode activeControlPanel = new ControlPanelNode("Localhost", 6767);

  /**
   * Starts the text based user interface.
   */
  public void start() {
    try {
      while (running) {
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
   * Displays application header.
   */
  private void displayHeader(String title) {
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
   * Displays the main control panel page.
   */
  private void displayControlPanelMainPage() throws IOException {
    displayHeader("SMART GREENHOUSE CLIENT");
    System.out.println("1. Lists all Greenhouses on the server");
    System.out.println("2. Select a greenhouse");
    System.out.println("3. Create a greenhouse");
    System.out.println("4. Remove a greenhouse");
    System.out.println("5. Disconnect from server");
    int choice = getUserChoice("Enter choice: ");

    switch (choice) {
      case 1 -> listAllGreenhouses();
      case 2 -> selectGreenhouseMenu();
      case 3 -> createGreenhouseMenu();
      case 4 -> removeGreenhouseMenu();
      case 5 -> disconnect();
      default -> System.out.println("Invalid choice!");
    }
  }

  /**
   * Lists all greenhouses.
   */
  private void listAllGreenhouses() throws IOException {
    GreenhouseListData greenhouseListData = activeControlPanel.getAllGreenhouses();
    for (int i = 0; i < greenhouseListData.getGreenhouses().size(); i++) {
      String greenhouseName = greenhouseListData.getGreenhouses().get(i).getGreenhouseName();
      System.out.println(i + 1 + ". " + greenhouseName);
    }
  }

  /**
   * Selects a greenhouse to control.
   */
  private void selectGreenhouseMenu() throws IOException {
    displayHeader("Available Greenhouses");
    listAllGreenhouses();

    int choice = getUserChoice("Select greenhouse: ");
    greenhouseControlMenu(choice);
  }

  /**
   * Menu for controlling a greenhouse.
   */
  private void greenhouseControlMenu(int greenhouseId) {
    displayHeader("Connected to Greenhouse #" + greenhouseId);
    System.out.println("1. View Sensor Data");
    System.out.println("2. View / Change Actuator Status");
    System.out.println("3. Back to Main Menu");

    int choice = getUserChoice("Enter choice: ");

    switch (choice) {
      case 1 -> viewSensorDataMenu(greenhouseId);
      case 2 -> viewChangeActuatorStatusMenu(greenhouseId);
      case 3 -> exit();
      default -> System.out.println("Invalid choice!");
    }
  }

  /**
   * Menu for viewing sensor data.
   */
  private void viewSensorDataMenu(int greenhouseId) {
    displayHeader("Sensor Data for Greenhouse #" + greenhouseId);
    System.out.println("Work in progress :'(");
  }

  /**
   * Menu for viewing/changing actuator status.
   */
  private void viewChangeActuatorStatusMenu(int greenhouseId) {
    displayHeader("Actuator Status for Greenhouse #" + greenhouseId);
    System.out.println("Work in progress :'(");
  }

  /**
   * Menu for creating a greenhouse.
   */
  private void createGreenhouseMenu() throws IOException {
    System.out.println("Choose greenhouse name: ");
    String name = scanner.nextLine();

    Information information = activeControlPanel.createGreenhouse(name);

    System.out.println(information.getInformation());
  }

  /**
   * Menu for removing a greenhouse from the server.
   */
  private void removeGreenhouseMenu() throws IOException {
    int id = getUserChoice("Enter ID of greenhouse to remove: ");

    Information information = activeControlPanel.removeGreenhouse(id);

    System.out.println(information.getInformation());
  }

  /**
   * Method to disconnect from the server.
   */
  private void disconnect() {
    try {
      activeControlPanel.disconnect();
      running = false;
    } catch (IOException e) {
      System.out.println("Error while disconnecting control panel");
    }
  }

  /**
   * Method to connect the control panel to a server.
   */
  private void connectToServer() {
    System.out.println("Enter a Server host's IP:");
    try {
      String host = scanner.nextLine();
      activeControlPanel.setHost(host);
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