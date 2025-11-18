package protocol;

import actuator.*;
import client.SensorNode;
import greenhouse.Greenhouse;
import javafx.util.Pair;
import protocol.command.*;
import sensor.Sensor;
import server.Server;

import java.util.*;

public class CommandHandler {

  public static String handleCommand(String message) {
    Message messageFromJSON = JSONHandler.deserializeFromJSONToMessage(message);

    Message reply = new Message();
    reply.setMessageID(String.valueOf(UUID.randomUUID()));
    reply.setTimestamp(System.currentTimeMillis());
    reply.setCorrelationID(messageFromJSON.getMessageID());
    reply.setSource("Server");

    switch (messageFromJSON.getMessageType()) {
      case "GET_ALL_GREENHOUSES" -> {
        ArrayList<Greenhouse> greenhouses = Server.getGreenhouseRegistry().getAllGreenhouses();
        reply.setBody(new GreenhouseListData(greenhouses));
        reply.setMessageType("GREENHOUSE_LIST_DATA");
        reply.setDestination(messageFromJSON.getSource());
        String replyJson = JSONHandler.serializeMessageToJSON(reply);
        return replyJson;
      }

      case "CREATE_GREENHOUSE" -> {
        CreateGreenhouse createGreenhouse = (CreateGreenhouse) messageFromJSON.getBody();
        String name = createGreenhouse.getName();

        Server.getGreenhouseRegistry().addGreenhouse(name);

        reply.setMessageType("INFORMATION");
        reply.setBody(new Information(name + " was added as a greenhouse."));
        reply.setDestination(messageFromJSON.getSource());
        String replyJson = JSONHandler.serializeMessageToJSON(reply);
        return replyJson;
      }

      case "REMOVE_GREENHOUSE" -> {
        RemoveGreenhouse removeGreenhouse = (RemoveGreenhouse) messageFromJSON.getBody();
        int id = removeGreenhouse.getId();

        Server.getGreenhouseRegistry().removeGreenhouse(id);

        reply.setMessageType("INFORMATION");
        reply.setBody(new Information("Greenhouse with id: " + id + " was removed."));
        reply.setDestination(messageFromJSON.getSource());
        String replyJson = JSONHandler.serializeMessageToJSON(reply);
        return replyJson;
      }

      case "DATA_REQUEST" -> {
        DataRequest dataRequest = (DataRequest) messageFromJSON.getBody();
        int greenhouseID = dataRequest.getGreenhouseID();
        Greenhouse greenhouse = Server.getGreenhouseRegistry().getGreenhouse(greenhouseID);
        String deviceID = dataRequest.getDeviceID();
        String deviceType = dataRequest.getdeviceType();


        switch (deviceType) {
          case "SENSOR" -> {
            reply.setMessageType("SENSOR_DATA");

            if (Objects.equals(deviceID, "ALL")) {
              HashMap<String, Pair<Double, String>> sensorDataHashMap = new HashMap<>();
              HashMap<String, Sensor> sensors = greenhouse.getSensorNode().getSensors();

              for (Map.Entry<String, Sensor> entry : sensors.entrySet()) {
                String sensorID = entry.getKey();
                Sensor sensor = entry.getValue();

                sensorDataHashMap.put(sensorID, new Pair<>(sensor.read(), sensor.getUnit()));
              }

              reply.setBody(new SensorData(sensorDataHashMap));
              reply.setDestination(messageFromJSON.getSource());

              String replyJson = JSONHandler.serializeMessageToJSON(reply);
              return replyJson;
            } else {
              Sensor sensor = greenhouse.getSensorNode().getSensor(deviceID);
              reply.setBody(new SensorData(sensor.getID(), sensor.read(), sensor.getUnit()));
              reply.setDestination(messageFromJSON.getSource());

              String replyJson = JSONHandler.serializeMessageToJSON(reply);
              return replyJson;
            }
          }

          case "ACTUATOR" -> {
            reply.setMessageType("ACTUATOR_DATA");

            if (Objects.equals(deviceID, "ALL")) {
              HashMap<String, Pair<Boolean, Integer>> actuatorDataHashMap = new HashMap<>();
              HashMap<String, Actuator> actuators = greenhouse.getSensorNode().getActuators();

              for (Map.Entry<String, Actuator> entry : actuators.entrySet()) {
                String actuatorID = entry.getKey();
                Actuator actuator = entry.getValue();

                actuatorDataHashMap.put(actuatorID, new Pair<>(actuator.isOn(), actuator.getPower()));
              }

              reply.setBody(new ActuatorData(actuatorDataHashMap));
              reply.setDestination(messageFromJSON.getSource());

              String replyJson = JSONHandler.serializeMessageToJSON(reply);
              return replyJson;
            } else {
              Sensor sensor = greenhouse.getSensorNode().getSensor(deviceID);
              reply.setBody(new SensorData(sensor.getID(), sensor.read(), sensor.getUnit()));
              reply.setDestination(messageFromJSON.getSource());

              String replyJson = JSONHandler.serializeMessageToJSON(reply);
              return replyJson;
            }
          }

          default ->  {
            return "Device type not found";
          }
        }
      }

      case "ADD_ACTUATOR" -> {
        AddActuator addActuator = (AddActuator) messageFromJSON.getBody();
        int greenhouseId = addActuator.getGreenhouseId();
        String actuatorType = addActuator.getActuatorType();

        Greenhouse greenhouse = Server.getGreenhouseRegistry().getGreenhouse(greenhouseId);
        Actuator actuator;

        switch (actuatorType) {
          case "Fan" -> actuator = new FanActuator();
          case "Heater" -> actuator = new HeaterActuator();
          case "Light" -> actuator = new LightActuator();
          case "Sprinkler" -> actuator = new SprinklerActuator();
          default -> {
            return "Actuator type not found";
            }
        }

        greenhouse.getSensorNode().addActuatorToNode(actuator);

        reply.setMessageType("INFORMATION");
        reply.setBody(new Information("Actuator with id " + actuator.getID() + " was added to sensor node"));
        reply.setDestination(messageFromJSON.getSource());
        String replyJson = JSONHandler.serializeMessageToJSON(reply);
        return replyJson;
      }

      default -> {
        return "Message type not found";
      }
    }
  }
}
