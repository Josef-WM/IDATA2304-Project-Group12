package ui;

import client.ControlPanelNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javafx.util.Pair;
import protocol.command.ActuatorData;
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
    GreenhouseListData greenhouseListData = activeControlPanel.getAllGreenhouses();

    if (choice <= greenhouseListData.getGreenhouses().size()) {
      greenhouseControlMenu(choice);
    } else {
      System.out.println("Invalid greenhouse selected");
      displayControlPanelMainPage();
    }
  }

  /**
   * Menu for controlling a greenhouse.
   */
  private void greenhouseControlMenu(int greenhouseId) throws IOException {
    displayHeader("Connected to Greenhouse #" + greenhouseId);
    System.out.println("1. View Sensor Data");
    System.out.println("2. View / Change Actuator Status");
    System.out.println("3. Add sensor to sensor node");
    System.out.println("4. Add actuator to sensor node");
    System.out.println("5. Back to Main Menu");

    int choice = getUserChoice("Enter choice: ");

    switch (choice) {
      case 1 -> viewSensorDataMenu(greenhouseId);
      case 2 -> viewChangeActuatorStatusMenu(greenhouseId);
      case 3 -> addActuatorToSensorNodeMenu(greenhouseId);
      case 4 -> addActuatorToSensorNodeMenu(greenhouseId);
      case 5 -> exit();
      default -> System.out.println("Invalid choice!");
    }
  }

  /**
   * Menu for viewing sensor data.
   */
  private void viewSensorDataMenu(int greenhouseId) throws IOException {
    displayHeader("Sensor Data for Greenhouse #" + greenhouseId);
    HashMap<String, Pair<Double, String>> sensorData = activeControlPanel.getAllSensorData(greenhouseId).getSensorDataHashMap();

    int index = 1;
    for (Map.Entry<String, Pair<Double, String>> entry : sensorData.entrySet()) {
      String sensorID = entry.getKey();
      Pair<Double, String> sensorInfo = entry.getValue();

      System.out.println(index + ". " + sensorID + ": " + sensorInfo.getKey() + " " + sensorInfo.getValue());
    }
  }

  /**
   * Menu for viewing/changing actuator status.
   */
  private void viewChangeActuatorStatusMenu(int greenhouseId) throws IOException {
    displayHeader("Actuator Status for Greenhouse " + greenhouseId);
    HashMap<String, Pair<Boolean, Integer>> actuatorData = activeControlPanel.getAllActuatorData(greenhouseId).getActuatorDataHashMap();

    ArrayList<String> actuatorIds = new ArrayList<>();
    int index = 1;
    for (Map.Entry<String, Pair<Boolean, Integer>> entry : actuatorData.entrySet()) {
      String actuatorID = entry.getKey();
      Pair<Boolean, Integer> actuatorInfo = entry.getValue();
      actuatorIds.add(actuatorID);

      String powerState;
      if (actuatorInfo.getKey()) {
        powerState = "ON";
      } else {
        powerState = "OFF";
      }

      System.out.println(index + ". " + actuatorID + ": [" + powerState + "] Power: " + actuatorInfo.getValue());
    }

    int choice = getUserChoice("Select actuator (or " + actuatorData.size() + " to quit): ");
    if (choice <= actuatorData.size()) {
      actuatorControlMenu(greenhouseId, actuatorIds.get(choice-1));
    } else {
      displayControlPanelMainPage();
    }

    greenhouseControlMenu(greenhouseId);
  }

  private void actuatorControlMenu(int greenhouseId, String actuatorId) throws IOException {
    ActuatorData actuatorData = activeControlPanel.getActuatorData(greenhouseId, actuatorId);

    String powerState;
    if (actuatorData.isOn()) {
      powerState = "ON";
    } else {
      powerState = "OFF";
    }

    String lowPowerSelected = "";
    String mediumPowerSelected = "";
    String highPowerSelected = "";

    switch (actuatorData.getPower()) {
      case 0 -> lowPowerSelected = " [SELECTED]";
      case 1 -> mediumPowerSelected = " [SELECTED]";
      case 2 -> highPowerSelected = " [SELECTED]";
    }

    displayHeader("Change status of actuator " + actuatorId);
    System.out.println("1. Toggle power status: (Currently [" + powerState + "])");
    System.out.println("2. Select LOW power" + lowPowerSelected);
    System.out.println("3. Select MEDIUM power" + mediumPowerSelected);
    System.out.println("4. Select HIGH power" + highPowerSelected);
    System.out.println("5. Back to Main Menu");

    int choice = getUserChoice("Enter choice: ");

    switch (choice) {
      case 1 -> {
        activeControlPanel.changeActuatorState(greenhouseId, actuatorId, !actuatorData.isOn());
        actuatorControlMenu(greenhouseId, actuatorId);
      }
      case 2 -> {
        activeControlPanel.changeActuatorPower(greenhouseId, actuatorId, 0);
        actuatorControlMenu(greenhouseId, actuatorId);
      }
      case 3 -> {
        activeControlPanel.changeActuatorPower(greenhouseId, actuatorId, 1);
        actuatorControlMenu(greenhouseId, actuatorId);
      }
      case 4 -> {
        activeControlPanel.changeActuatorPower(greenhouseId, actuatorId, 2);
        actuatorControlMenu(greenhouseId, actuatorId);
      }
      case 5 -> viewChangeActuatorStatusMenu(greenhouseId);
      default -> System.out.println("Invalid choice!");
    }
  }

  private void addActuatorToSensorNodeMenu(int greenhouseId) throws IOException {
    displayHeader("Add actuator to sensor node in greenhouse " + greenhouseId);

    System.out.println("1. Fan");
    System.out.println("2. Heater");
    System.out.println("3. Light");
    System.out.println("4. Sprinkler");
    System.out.println("5. Back to Main Menu");

    int choice = getUserChoice("Enter choice: ");

    switch (choice) {
      case 1 -> activeControlPanel.addActuatorToSensorNode(greenhouseId, "Fan");
      case 2 -> activeControlPanel.addActuatorToSensorNode(greenhouseId, "Heater");
      case 3 -> activeControlPanel.addActuatorToSensorNode(greenhouseId, "Light");
      case 4 -> activeControlPanel.addActuatorToSensorNode(greenhouseId, "Sprinkler");
      case 5 -> greenhouseControlMenu(greenhouseId);
      default -> System.out.println("Invalid choice!");
    }
    greenhouseControlMenu(greenhouseId);
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

  public void pressEnterToContinue() {
    System.out.println("Press enter to continue...");
    scanner.nextLine();
  }
}