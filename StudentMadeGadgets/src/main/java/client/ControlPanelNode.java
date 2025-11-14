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

  public void setHost(String host) {
    this.host = host;
  }

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

  public void disconnect() throws IOException {
    socket.close();
  }

  public Socket getSocket() {
    return this.socket;
  }

  public boolean isConnected() {
    return (this.socket != null);
  }
}
