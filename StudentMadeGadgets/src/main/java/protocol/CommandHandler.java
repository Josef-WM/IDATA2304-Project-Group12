package protocol;

import greenhouse.Greenhouse;
import protocol.command.GreenhouseListData;
import server.Server;

import java.util.ArrayList;

public class CommandHandler {

  public static String handleCommand(String message) {
    Message messageFromJSON = JSONHandler.deserializeFromJSONToMessage(message);
    switch (messageFromJSON.getMessageType()) {
      case "GET_ALL_GREENHOUSES" -> {
        Message reply = new Message();
        reply.setTimestamp(13L);
        ArrayList<Greenhouse> greenhouses = Server.getGreenhouseRegistry().getAllGreenhouses();
        reply.setBody(new GreenhouseListData(greenhouses));
        reply.setMessageType("GREENHOUSE_LIST_DATA");
        reply.setMessageID("99999999999999999999");
        reply.setCorrelationID(messageFromJSON.getMessageID());
        reply.setSource("Server");
        reply.setDestination(messageFromJSON.getSource());
        String replyJson = JSONHandler.serializeMessageToJSON(reply);
        return replyJson;
      }
      default -> {
        System.out.println("No message type found! LOLOLOL!");
        return "No message type found";
      }
    }
  }
}
