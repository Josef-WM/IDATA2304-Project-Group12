package protocol;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import protocol.command.*;

public class JSONHandler {

  public static String serializeMessageToJSON(Message message) {
    Gson gson = new Gson();

    return gson.toJson(message);
  }

  public static Message deserializeFromJSONToMessage(String json) {
    Gson gson = new Gson();

    JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
    String type = String.valueOf(jsonObject.get("type"));

    Message message = gson.fromJson(json, Message.class);

    JsonElement bodyElement = jsonObject.get("body");
    if (bodyElement != null && bodyElement.isJsonObject()) {
      message.setBody(desirializeBody(bodyElement.getAsJsonObject(), type));
    }

    return gson.fromJson(json, message.getClass());
  }

  public static Command desirializeBody(JsonObject bodyJson, String messageType) {
    Gson gson = new Gson();

    return switch (messageType) {
      case "ACTUATOR_COMMAND" -> gson.fromJson(bodyJson, ActuatorCommand.class);
      case "ACTUATOR_DATA" -> gson.fromJson(bodyJson, ActuatorData.class);
      case "DATA_REQUEST" -> gson.fromJson(bodyJson, DataRequest.class);
      case "SENSOR_DATA" -> gson.fromJson(bodyJson, SensorData.class);
      default -> null;
    };
  }

}
