package protocol;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import protocol.command.*;

public class JSONHandler {

  public static String serializeMessageToJSON(Message message) {
    Gson gson = new Gson();

    return gson.toJson(message);
  }

  public static Message deserializeFromJSONToMessage(String json) {
    // Check for null or empty JSON first
    if (json == null || json.trim().isEmpty()) {
      throw new IllegalArgumentException("JSON string is null or empty");
    }

    Gson gson = new Gson();
    JsonObject root;

    try {
      root = gson.fromJson(json, JsonObject.class);
    } catch (JsonSyntaxException e) {
      throw new IllegalArgumentException("Invalid JSON syntax: " + e.getMessage(), e);
    }

    // Check if parsing resulted in null
    if (root == null) {
      throw new IllegalArgumentException("Failed to parse JSON - root is null. JSON: " + json);
    }

    // Create the Message object
    Message message = new Message();

    // Basic fields - with required field checking
    if (!root.has("messageType")) {
      throw new IllegalArgumentException("Missing required field: messageType");
    }

    String messageType = root.get("messageType").getAsString();
    message.setMessageType(messageType);

    // Optional fields
    if (root.has("source") && !root.get("source").isJsonNull()) {
      message.setSource(root.get("source").getAsString());
    }
    if (root.has("destination") && !root.get("destination").isJsonNull()) {
      message.setDestination(root.get("destination").getAsString());
    }
    if (root.has("messageID") && !root.get("messageID").isJsonNull()) {
      message.setMessageID(root.get("messageID").getAsString());
    }
    if (root.has("correlationID") && !root.get("correlationID").isJsonNull()) {
      message.setCorrelationID(root.get("correlationID").getAsString());
    }
    if (root.has("timestamp") && !root.get("timestamp").isJsonNull()) {
      message.setTimestamp(root.get("timestamp").getAsLong());
    }

    // Body
    if (root.has("body") && root.get("body").isJsonObject()) {
      JsonObject bodyJson = root.getAsJsonObject("body");
      Command body = deserializeBody(bodyJson, messageType);
      message.setBody(body);
    }

    return message;
  }


  public static Command deserializeBody(JsonObject bodyJson, String messageType) {
    Gson gson = new Gson();

    return switch (messageType) {
      case "ACTUATOR_COMMAND" -> gson.fromJson(bodyJson, ActuatorCommand.class);
      case "ACTUATOR_DATA" -> gson.fromJson(bodyJson, ActuatorData.class);
      case "DATA_REQUEST" -> gson.fromJson(bodyJson, DataRequest.class);
      case "SENSOR_DATA" -> gson.fromJson(bodyJson, SensorData.class);
      case "GREENHOUSE_LIST_DATA" -> gson.fromJson(bodyJson, GreenhouseListData.class);
      case "CREATE_GREENHOUSE" -> gson.fromJson(bodyJson, CreateGreenhouse.class);
      case "REMOVE_GREENHOUSE" -> gson.fromJson(bodyJson, RemoveGreenhouse.class);
      case "INFORMATION" -> gson.fromJson(bodyJson, Information.class);
      case "ADD_ACTUATOR" -> gson.fromJson(bodyJson, AddActuator.class);
      default -> null;
    };
  }

}
