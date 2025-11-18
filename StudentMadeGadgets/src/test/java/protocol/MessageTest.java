package protocol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import protocol.command.Command;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Message class.
 *
 * <p>This test suite verifies correct behavior for both positive and negative scenarios.
 * It follows the Arrange–Act–Assert (AAA) structure and includes tests for constructors,
 * setters, and handling of null values.</p>
 */
class MessageTest {

  private Message emptyMessage;

  /**
   * Minimal concrete implementation of Command for testing purposes.
   */
  private static class DummyCommand implements Command {
    private final String value;
    DummyCommand(String value) { this.value = value; }
    public String getValue() { return value; }
  }

  /**
   * Initializes a fresh Message object before every test.
   */
  @BeforeEach
  void setUp() {
    emptyMessage = new Message();
  }

  /**
   * Tests that the constructor with correlation ID correctly sets all fields.
   */
  @Test
  void constructorWithCorrelationId_Positive_SetsAllFieldsCorrectly() {
    // Arrange
    Command cmd = new DummyCommand("test");

    // Act
    Message msg = new Message(
            "node1",
            "controlPanel",
            "sensor_data",
            "msg001",
            "corr001",
            123L,
            cmd
    );

    // Assert
    assertEquals("node1", msg.getSource());
    assertEquals("controlPanel", msg.getDestination());
    assertEquals("sensor_data", msg.getMessageType());
    assertEquals("msg001", msg.getMessageID());
    assertEquals("corr001", msg.getCorrelationID());
    assertEquals(123L, msg.getTimestamp());
    assertEquals(cmd, msg.getBody());
  }

  /**
   * Tests the constructor WITHOUT a correlation ID,
   * ensuring correlationID remains null.
   */
  @Test
  void constructorWithoutCorrelationId_Positive_CreatesMessageSuccessfully() {
    // Arrange
    Command cmd = new DummyCommand("abc");

    // Act
    Message msg = new Message(
            "node2",
            "server",
            "command",
            "msg777",
            456L,
            cmd
    );

    // Assert
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
   */
  @Test
  void setters_Positive_UpdateFieldsSuccessfully() {
    // Arrange
    DummyCommand cmd = new DummyCommand("body");

    // Act
    emptyMessage.setSource("A");
    emptyMessage.setDestination("B");
    emptyMessage.setMessageType("update");
    emptyMessage.setMessageID("id001");
    emptyMessage.setCorrelationID("corrX");
    emptyMessage.setTimestamp(999L);
    emptyMessage.setBody(cmd);

    // Assert
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
   */
  @Test
  void setters_Negative_AllowNullValuesExceptTimestamp() {
    // Arrange

    // Act
    emptyMessage.setSource(null);
    emptyMessage.setDestination(null);
    emptyMessage.setMessageType(null);
    emptyMessage.setMessageID(null);
    emptyMessage.setCorrelationID(null);
    // timestamp is NOT set to null (illegal)
    emptyMessage.setBody(null);

    // Assert
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
   * Tests that setting the timestamp to null throws a NullPointerException,
   * since the underlying field is a primitive long.
   */
  @Test
  void setTimestamp_Negative_NullValueThrowsNullPointerException() {
    // Arrange

    // Act & Assert
    assertThrows(NullPointerException.class,
            () -> emptyMessage.setTimestamp(null),
            "Setting timestamp to null must throw NullPointerException");
  }

  /**
   * Tests that a null body is accepted by the constructor.
   */
  @Test
  void constructor_Negative_AllowsNullBody() {
    // Arrange

    // Act
    Message msg = new Message(
            "nodeX",
            "nodeY",
            "event",
            "msg123",
            null,
            500L,
            null
    );

    // Assert
    assertNull(msg.getBody());
  }

  /**
   * Tests that the constructor accepts null values for all string fields.
   */
  @Test
  void constructor_Negative_AllowsNullStrings() {
    // Arrange

    // Act
    Message msg = new Message(
            null,   // source
            null,   // destination
            null,   // type
            null,   // messageID
            null,   // correlation ID
            0L,
            new DummyCommand("x")
    );

    // Assert
    assertNull(msg.getSource());
    assertNull(msg.getDestination());
    assertNull(msg.getMessageType());
    assertNull(msg.getMessageID());
    assertNull(msg.getCorrelationID());
  }

  /**
   * Tests that empty strings are allowed for messageID.
   */
  @Test
  void setMessageID_Negative_AllowsEmptyString() {
    // Arrange

    // Act
    emptyMessage.setMessageID("");

    // Assert
    assertEquals("", emptyMessage.getMessageID());
  }
}
