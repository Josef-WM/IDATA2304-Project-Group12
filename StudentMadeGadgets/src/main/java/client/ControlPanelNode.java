package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import protocol.Protocol;

/**
 * ControlPanelNode class, includes methods for connecting
 * to and sending commands to the server.
 */
public class ControlPanelNode {
  String host;
  int port;
  Socket socket;

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
      System.out.println("Failed to connect to the server " + e.getMessage());
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
}
