package Client;

import java.net.Socket;
import java.io.IOException;

public class ControlPanelNodes {
  public static void main(String args[]) throws IOException {
    try {
      Socket socket = new Socket("Localhost", 6767);
      System.out.println("You connected to the server");
    } catch(IOException e) {
      System.out.println("Failed to connect to the server");
    }
  }
}
