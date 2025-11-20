package client;

import com.google.gson.JsonObject;
import greenhouse.Greenhouse;
import javafx.util.Pair;
import protocol.CommandHandler;
import protocol.JSONHandler;
import protocol.Message;
import protocol.Protocol;
import protocol.command.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * ControlPanelNode class, includes methods for connecting
 * to and sending commands to the server.
 */
public class ControlPanelNode {
  private String host;
  private int port;
  private Socket socket;
  /**
   * Constructor for the ControlPanelNode class.
   *
   * @param host the host/ip of the server to connect to, defaults to localhost
   * @param port the port number to use, defaults to 6767 per the protocol specifications
   */
  public ControlPanelNode(String host, int port) {
    this.host = host;
    this.port = port;
  }

  // Set the control panel's connection host
  public void setHost(String host) {
    this.host = host;
  }

  // Set the control panel's connection port
  public void setPort(int port) {
    this.port = port;
  }

  /**
   * Connects to the server, specified with the host and port.
   */
  public void connect() {
    try {
      socket = new Socket(host,port);
    } catch (IOException e) {
      System.out.println("Failed to connect to the server: " + e.getMessage());
    }
  }

  // Disconnects the panel from the server
  public void disconnect() throws IOException {
    socket.close();
    socket = null;
  }

  // Returns the connection socket
  public Socket getSocket() {
    return this.socket;
  }

  /**
   * Returns True if the panel is connected to a server
   * Returns False if the panel is not connected to a server
   */
  public boolean isConnected() {
    return this.socket != null && socket.isConnected() && !socket.isClosed();
  }

  public GreenhouseListData getAllGreenhouses() throws IOException {
    Message message = new Message();
    message.setMessageType("GET_ALL_GREENHOUSES");
    message.setMessageID(String.valueOf(UUID.randomUUID()));
    message.setTimestamp(System.currentTimeMillis());
    String jsonMessage = JSONHandler.serializeMessageToJSON(message);
    Protocol protocol = new Protocol(this.socket);
    protocol.sendMessage(jsonMessage);
    String reply = protocol.readMessage();
    Message replyMessage = JSONHandler.deserializeFromJSONToMessage(reply);
    GreenhouseListData greenhouseListData = (GreenhouseListData) replyMessage.getBody();
    return greenhouseListData;
  }

  public Information createGreenhouse(String name) throws IOException {
    Message message = new Message();
    message.setMessageType("CREATE_GREENHOUSE");
    message.setMessageID(String.valueOf(UUID.randomUUID()));
    message.setTimestamp(System.currentTimeMillis());
    message.setBody(new CreateGreenhouse(name));
    String jsonMessage = JSONHandler.serializeMessageToJSON(message);
    Protocol protocol = new Protocol(this.socket);
    protocol.sendMessage(jsonMessage);

    String reply = protocol.readMessage();
    Message replyMessage = JSONHandler.deserializeFromJSONToMessage(reply);
    Information information = (Information) replyMessage.getBody();
    return information;
  }

  public Information removeGreenhouse(int id) throws IOException {
    Message message = new Message();
    message.setMessageType("REMOVE_GREENHOUSE");
    message.setMessageID(String.valueOf(UUID.randomUUID()));
    message.setTimestamp(System.currentTimeMillis());
    message.setBody(new RemoveGreenhouse(id));
    String jsonMessage = JSONHandler.serializeMessageToJSON(message);
    Protocol protocol = new Protocol(this.socket);
    protocol.sendMessage(jsonMessage);

    String reply = protocol.readMessage();
    Message replyMessage = JSONHandler.deserializeFromJSONToMessage(reply);
    Information information = (Information) replyMessage.getBody();
    return information;
  }

  public SensorData getAllSensorData(int greenhouseId) throws IOException {
    Message message = new Message();
    message.setMessageType("DATA_REQUEST");
    message.setMessageID(String.valueOf(UUID.randomUUID()));
    message.setTimestamp(System.currentTimeMillis());
    message.setBody(new DataRequest(greenhouseId, "ALL", "SENSOR"));
    String jsonMessage = JSONHandler.serializeMessageToJSON(message);
    Protocol protocol = new Protocol(this.socket);
    protocol.sendMessage(jsonMessage);

    String reply = protocol.readMessage();
    Message replyMessage = JSONHandler.deserializeFromJSONToMessage(reply);
    SensorData sensorData = (SensorData) replyMessage.getBody();
    return sensorData;
  }

  public ActuatorData getActuatorData(int greenhouseId, String actuatorId) throws IOException {
    Message message = new Message();
    message.setMessageType("DATA_REQUEST");
    message.setMessageID(String.valueOf(UUID.randomUUID()));
    message.setTimestamp(System.currentTimeMillis());
    message.setBody(new DataRequest(greenhouseId, actuatorId, "ACTUATOR"));
    String jsonMessage = JSONHandler.serializeMessageToJSON(message);
    Protocol protocol = new Protocol(this.socket);
    protocol.sendMessage(jsonMessage);

    String reply = protocol.readMessage();
    Message replyMessage = JSONHandler.deserializeFromJSONToMessage(reply);
    ActuatorData actuatorData = (ActuatorData) replyMessage.getBody();
    return actuatorData;
  }

  public ActuatorData getAllActuatorData(int greenhouseId) throws IOException {
    Message message = new Message();
    message.setMessageType("DATA_REQUEST");
    message.setMessageID(String.valueOf(UUID.randomUUID()));
    message.setTimestamp(System.currentTimeMillis());
    message.setBody(new DataRequest(greenhouseId, "ALL", "ACTUATOR"));
    String jsonMessage = JSONHandler.serializeMessageToJSON(message);
    Protocol protocol = new Protocol(this.socket);
    protocol.sendMessage(jsonMessage);

    String reply = protocol.readMessage();
    Message replyMessage = JSONHandler.deserializeFromJSONToMessage(reply);
    ActuatorData actuatorData = (ActuatorData) replyMessage.getBody();
    return actuatorData;
  }

  public Information changeActuatorState(int greenhouseId, String actuatorId, boolean state) throws IOException {
    Message message = new Message();
    message.setMessageType("ACTUATOR_COMMAND");
    message.setMessageID(String.valueOf(UUID.randomUUID()));
    message.setTimestamp(System.currentTimeMillis());
    message.setBody(new ActuatorCommand(greenhouseId, actuatorId, state));
    String jsonMessage = JSONHandler.serializeMessageToJSON(message);
    Protocol protocol = new Protocol(this.socket);
    protocol.sendMessage(jsonMessage);

    String reply = protocol.readMessage();
    Message replyMessage = JSONHandler.deserializeFromJSONToMessage(reply);
    Information information = (Information) replyMessage.getBody();
    return information;
  }

  public Information changeActuatorPower(int greenhouseId, String actuatorId, int power) throws IOException {
    Message message = new Message();
    message.setMessageType("ACTUATOR_COMMAND");
    message.setMessageID(String.valueOf(UUID.randomUUID()));
    message.setTimestamp(System.currentTimeMillis());
    message.setBody(new ActuatorCommand(greenhouseId, actuatorId, power));
    String jsonMessage = JSONHandler.serializeMessageToJSON(message);
    Protocol protocol = new Protocol(this.socket);
    protocol.sendMessage(jsonMessage);

    String reply = protocol.readMessage();
    Message replyMessage = JSONHandler.deserializeFromJSONToMessage(reply);
    Information information = (Information) replyMessage.getBody();
    return information;
  }

  public Information addActuatorToSensorNode(int greenhouseId, String actuatorType) throws IOException {
    Message message = new Message();
    message.setMessageType("ADD_ACTUATOR");
    message.setMessageID(String.valueOf(UUID.randomUUID()));
    message.setTimestamp(System.currentTimeMillis());
    message.setBody(new AddActuator(greenhouseId, actuatorType));
    String jsonMessage = JSONHandler.serializeMessageToJSON(message);
    Protocol protocol = new Protocol(this.socket);
    protocol.sendMessage(jsonMessage);

    String reply = protocol.readMessage();
    Message replyMessage = JSONHandler.deserializeFromJSONToMessage(reply);
    Information information = (Information) replyMessage.getBody();
    return information;
  }

  public Information addSensorToSensorNode(int greenhouseId, String sensorType) throws IOException {
    Message message = new Message();
    message.setMessageType("ADD_SENSOR");
    message.setMessageID(String.valueOf(UUID.randomUUID()));
    message.setTimestamp(System.currentTimeMillis());
    message.setBody(new AddSensor(greenhouseId, sensorType));
    String jsonMessage = JSONHandler.serializeMessageToJSON(message);
    Protocol protocol = new Protocol(this.socket);
    protocol.sendMessage(jsonMessage);

    String reply = protocol.readMessage();
    Message replyMessage = JSONHandler.deserializeFromJSONToMessage(reply);
    Information information = (Information) replyMessage.getBody();
    return information;
  }
}