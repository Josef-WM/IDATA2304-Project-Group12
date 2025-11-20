package protocol;

import protocol.command.Command;

/**
 * Represents a message in the communication protocol.
 */
public final class Message {
  private String source;
  private String destination;
  private String messageType;
  private String messageID;
  private String correlationID;
  private long timestamp;
  private Command body;

  /**
   * Constructor for Message.
   *
   * @param source        the source of the message
   * @param destination   the destination of the message
   * @param messageType   the type of the message
   * @param messageID     the unique identifier for the message
   * @param correlationID the correlation identifier for the message
   * @param timestamp     the timestamp of the message
   * @param body          the body of the message
   */
  public Message(String source, String destination, String messageType, String messageID, String correlationID, Long timestamp, Command body) {
    this.source = source;
    this.destination = destination;
    this.messageType = messageType;
    this.messageID = messageID;
    this.correlationID = correlationID;
    this.timestamp = timestamp;
    this.body = body;
  }

  /**
   * Constructor for Message without correlationID.
   *
   * @param source      the source of the message
   * @param destination the destination of the message
   * @param messageType the type of the message
   * @param messageID   the unique identifier for the message
   * @param timestamp   the timestamp of the message
   * @param body        the body of the message
   */
  public Message(String source, String destination, String messageType, String messageID, Long timestamp, Command body) {
    this.source = source;
    this.destination = destination;
    this.messageType = messageType;
    this.messageID = messageID;
    this.timestamp = timestamp;
    this.body = body;
  }

  /**
   * Empty constructor for Message.
   */
  public Message() {}

  /**
   * Gets the source of the message.
   *
   * @return the source of the message
   */
  public String getSource() {
    return this.source;
  }

  /**
   * Sets the source of the message.
   *
   * @param source the source to set
   */
  public void setSource(String source) {
    this.source = source;
  }

  /**
   * Gets the destination of the message.
   *
   * @return the destination of the message
   */
  public String getDestination() {
    return this.destination;
  }

  /**
   * Sets the destination of the message.
   *
   * @param destination the destination to set
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
   * Gets the type of the message.
   *
   * @return the type of the message
   */
  public String getMessageType() {
    return this.messageType;
  }

  /**
   * Sets the type of the message.
   *
   * @param messageType the type to set
   */
  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  /**
   * Gets the unique identifier of the message.
   *
   * @return the unique identifier of the message
   */
  public String getMessageID() {
    return this.messageID;
  }

  /**
   * Sets the unique identifier of the message.
   *
   * @param messageID the unique identifier to set
   */
  public void setMessageID(String messageID) {
    this.messageID = messageID;
  }

  /**
   * Gets the correlation identifier of the message.
   *
   * @return the correlation identifier of the message
   */
  public String getCorrelationID() {
    return this.correlationID;
  }

  /**
   * Sets the correlation identifier of the message.
   *
   * @param correlationID the correlation identifier to set
   */
  public void setCorrelationID(String correlationID) {
    this.correlationID = correlationID;
  }

  /**
   * Gets the timestamp of the message.
   *
   * @return the timestamp of the message
   */
  public Long getTimestamp() {
    return this.timestamp;
  }

  /**
   * Sets the timestamp of the message.
   *
   * @param timestamp the timestamp to set
   */
  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * Gets the body of the message.
   *
   * @return the body of the message
   */
  public Command getBody() {
    return this.body;
  }

  /**
   * Sets the body of the message.
   *
   * @param body the body to set
   */
  public void setBody(Command body) {
    this.body = body;
  }
}
