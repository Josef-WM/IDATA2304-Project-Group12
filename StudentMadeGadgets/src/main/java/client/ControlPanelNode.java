package client;

import com.google.gson.JsonObject;
import greenhouse.Greenhouse;
import protocol.CommandHandler;
import protocol.JSONHandler;
import protocol.Message;
import protocol.Protocol;
import protocol.command.CreateGreenhouse;
import protocol.command.GreenhouseListData;
import protocol.command.Information;
import protocol.command.RemoveGreenhouse;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

/**
 * ControlPanelNode class, includes methods for connecting
 * to and sending commands to the server.
 */
public class ControlPanelNode {
  private String host;
  private int port;
  private Socket socket;
  private int id;

  /**
   * Constructor for the ControlPanelNode class.
   *
   * @param host the host/ip of the server to connect to, defaults to localhost
   * @param port the port number to use, defaults to 6767 per the protocol specifications
   */
  public ControlPanelNode(String host, int port) {
    this.host = host;
    this.port = port;
    this.id = 1;
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
}