package protocol;

import com.google.gson.Gson;

public class JSONHandler {

  public static String serializeMessageToJSON(Message message) {
    Gson gson = new Gson();

    return gson.toJson(message);
  }

}
