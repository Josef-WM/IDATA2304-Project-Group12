package Server;

import Actuator.FanActuator;
import Greenhouse.Greenhouse;
import Protocol.Protocol;
import Sensor.TemperatureSensor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  private static final Greenhouse greenhouse = new Greenhouse("Greenhouse A");
  private static final TemperatureSensor tempSensor = new TemperatureSensor("TEMP-A",greenhouse);
  private static final FanActuator fan = new FanActuator(1,greenhouse);

  public static void main(String[] args) throws IOException {
    try (ServerSocket serverSocket = new ServerSocket(6767)) {
      System.out.println("Server is now listening on 6767");
      while (true) {
        Socket socket = serverSocket.accept();
        new Thread(() -> handle(socket)).start();
      }
    }
  }

  public static void handle(Socket socket) {
    try (Protocol protocol = new Protocol(socket)) {
      System.out.println("A client has connected: " + socket.getRemoteSocketAddress());

      String message;
      while ((message = protocol.readMessage()) != null) {
        message = message.trim();
        System.out.println("Got: " + message);

        if (message.equals("exit")) {
          protocol.sendMessage("Goodbye");
          break;
        }

        String reply = handleCommand(message);
        protocol.sendMessage(reply);
      }
    } catch (IOException e) {
      System.out.println("Client error: " + e.getMessage());
    }
  }

  private static String handleCommand(String message) {
    String[] parts = message.split("\\s+");
    if (parts.length < 2) {
      return "ERROR: Command must have at least one argument (Example: READ temp)";
    }

    String verb = parts[0].toUpperCase();
    String target = parts[1].toUpperCase();

    switch (verb) {
      case "READ":
        if (target.equals("TEMP")) {
          double value = tempSensor.read();
          return "Temperature: " + value + " " + tempSensor.getUnit();
        }
        return "ERROR: Unknown READ target: " + target;

      case "TOGGLE":
        if (target.equals("FAN")) {
          if (fan.isOn()) {
            fan.turnOff();
            return "Fan turned OFF";
          }
          else {
            fan.turnOn();
            return "Fan turned ON";
          }
        }
        return "ERROR: Unknown TOGGLE target: " + target;

        default:
          return "ERROR: Unknown command: " + verb;
    }
  }
}