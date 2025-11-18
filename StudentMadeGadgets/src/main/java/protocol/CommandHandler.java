package protocol;

import greenhouse.Greenhouse;
import protocol.command.CreateGreenhouse;
import protocol.command.GreenhouseListData;
import protocol.command.Information;
import protocol.command.RemoveGreenhouse;
import server.Server;

import java.util.ArrayList;
import java.util.UUID;

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
      default -> {
        System.out.println("No message type found! LOLOLOL!");
        return "No message type found";
      }
    }
  }
}
