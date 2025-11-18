package protocol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import protocol.command.Command;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the class Message for constructors, setters, and null handling.
 */
class MessageTest {

  private Message emptyMessage;

  private static class DummyCommand implements Command {
    private final String value;
    DummyCommand(String value) { this.value = value; }
    public String getValue() { return value; }
  }

  /**
   * Initializes a fresh Message object before every test.
   * Expected outcome: fresh Message instance available in each test.
   */
  @BeforeEach
  void setUp() {
    emptyMessage = new Message();
  }

  /**
   * Tests that the constructor with correlation ID correctly sets all fields.
   * Expected outcome: all getters return values passed to constructor.
   */
  @Test
  void constructorWithCorrelationId_Positive_SetsAllFieldsCorrectly() {
    Command cmd = new DummyCommand("test");
    Message msg = new Message(
            "node1", "controlPanel", "sensor_data", "msg001", "corr001", 123L, cmd);
    assertEquals("node1", msg.getSource());
    assertEquals("controlPanel", msg.getDestination());
    assertEquals("sensor_data", msg.getMessageType());
    assertEquals("msg001", msg.getMessageID());
    assertEquals("corr001", msg.getCorrelationID());
    assertEquals(123L, msg.getTimestamp());
    assertEquals(cmd, msg.getBody());
  }

  /**
   * Tests the constructor WITHOUT a correlation ID; correlationID should be null.
   * Expected outcome: correlationID is null and other fields set.
   */
  @Test
  void constructorWithoutCorrelationId_Positive_CreatesMessageSuccessfully() {
    Command cmd = new DummyCommand("abc");
    Message msg = new Message(
            "node2", "server", "command", "msg777", 456L, cmd);
    assertEquals("node2", msg.getSource());
    assertEquals("server", msg.getDestination());
    assertEquals("command", msg.getMessageType());
    assertEquals("msg777", msg.getMessageID());
    assertEquals(456L, msg.getTimestamp());
    assertEquals(cmd, msg.getBody());
    assertNull(msg.getCorrelationID());
  }

  /**
   * Tests that all setter methods correctly update their fields with valid values.
   * Expected outcome: getters return the newly set values.
   */
  @Test
  void setters_Positive_UpdateFieldsSuccessfully() {
    DummyCommand cmd = new DummyCommand("body");
    emptyMessage.setSource("A");
    emptyMessage.setDestination("B");
    emptyMessage.setMessageType("update");
    emptyMessage.setMessageID("id001");
    emptyMessage.setCorrelationID("corrX");
    emptyMessage.setTimestamp(999L);
    emptyMessage.setBody(cmd);

    assertEquals("A", emptyMessage.getSource());
    assertEquals("B", emptyMessage.getDestination());
    assertEquals("update", emptyMessage.getMessageType());
    assertEquals("id001", emptyMessage.getMessageID());
    assertEquals("corrX", emptyMessage.getCorrelationID());
    assertEquals(999L, emptyMessage.getTimestamp());
    assertEquals(cmd, emptyMessage.getBody());
  }

  /**
   * Tests that all string-based fields and the body field accept null values,
   * and that the timestamp remains at its default (0L).
   * Expected outcome: nulls accepted; timestamp still 0L.
   */
  @Test
  void setters_Negative_AllowNullValuesExceptTimestamp() {
    emptyMessage.setSource(null);
    emptyMessage.setDestination(null);
    emptyMessage.setMessageType(null);
    emptyMessage.setMessageID(null);
    emptyMessage.setCorrelationID(null);
    emptyMessage.setBody(null);

    assertNull(emptyMessage.getSource());
    assertNull(emptyMessage.getDestination());
    assertNull(emptyMessage.getMessageType());
    assertNull(emptyMessage.getMessageID());
    assertNull(emptyMessage.getCorrelationID());
    assertEquals(0L, emptyMessage.getTimestamp(),
            "Timestamp should remain at default (0L) because it cannot be null");
    assertNull(emptyMessage.getBody());
  }

  /**
   * Tests that setting the timestamp to null throws a NullPointerException.
   * Expected outcome: NullPointerException is thrown.
   */
  @Test
  void setTimestamp_Negative_NullValueThrowsNullPointerException() {
    assertThrows(NullPointerException.class,
            () -> emptyMessage.setTimestamp(null),
            "Setting timestamp to null must throw NullPointerException");
  }

  /**
   * Tests that a null body is accepted by the constructor.
   * Expected outcome: getBody() returns null for a constructor-created message.
   */
  @Test
  void constructor_Negative_AllowsNullBody() {
    Message msg = new Message(
            "nodeX", "nodeY", "event", "msg123", null, 500L, null);
    assertNull(msg.getBody());
  }

  /**
   * Tests that the constructor accepts null values for all string fields.
   * Expected outcome: all string getters return null.
   */
  @Test
  void constructor_Negative_AllowsNullStrings() {
    Message msg = new Message(
            null, null, null, null, null, 0L, new DummyCommand("x"));
    assertNull(msg.getSource());
    assertNull(msg.getDestination());
    assertNull(msg.getMessageType());
    assertNull(msg.getMessageID());
    assertNull(msg.getCorrelationID());
  }

  /**
   * Tests that empty strings are allowed for messageID.
   * Expected outcome: getMessageID() returns empty string after set.
   */
  @Test
  void setMessageID_Negative_AllowsEmptyString() {
    emptyMessage.setMessageID("");
    assertEquals("", emptyMessage.getMessageID());
  }
}

