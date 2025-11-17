package protocol;

import protocol.command.Command;

/**
 * Javadoc placeholder.
 */
public final class Message {
  private String source;
  private String destination;
  private String messageType;
  private String messageID;
  private String correlationID;
  private long timestamp;
  private Command body;

  public Message(String source, String destination, String messageType, String messageID, String correlationID, Long timestamp, Command body) {
    this.source = source;
    this.destination = destination;
    this.messageType = messageType;
    this.messageID = messageID;
    this.correlationID = correlationID;
    this.timestamp = timestamp;
    this.body = body;
  }

  public Message(String source, String destination, String messageType, String messageID, Long timestamp, Command body) {
    this.source = source;
    this.destination = destination;
    this.messageType = messageType;
    this.messageID = messageID;
    this.timestamp = timestamp;
    this.body = body;
  }

  public Message() {}

  public String getSource() {
    return this.source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getDestination() {
    return this.destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getMessageType() {
    return this.messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public String getMessageID() {
    return this.messageID;
  }

  public void setMessageID(String messageID) {
    this.messageID = messageID;
  }

  public String getCorrelationID() {
    return this.correlationID;
  }

  public void setCorrelationID(String correlationID) {
    this.correlationID = correlationID;
  }

  public Long getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public Command getBody() {
    return this.body;
  }

  public void setBody(Command body) {
    this.body = body;
  }
}
