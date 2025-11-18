package protocol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import protocol.command.Command;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Message} class.
 *
 * <p>Contains positive and negative test cases following the
 * Arrange–Act–Assert (AAA) principle.</p>
 */
class MessageTest {

  private Message emptyMessage;

  /**
   * Minimal concrete Command implementation for testing.
   */
  private static class DummyCommand implements Command {
    private final String value;

    DummyCommand(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  @BeforeEach
  void setUp() {
    // Arrange
    emptyMessage = new Message();
  }

  // --------------------------------------------------------------
  //   POSITIVE TESTS
  // --------------------------------------------------------------

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

  // --------------------------------------------------------------
  //   NEGATIVE TESTS
  // --------------------------------------------------------------

  @Test
  void setters_Negative_AllowNullValuesWithoutCrashing() {
    // Arrange

    // Act
    emptyMessage.setSource(null);
    emptyMessage.setDestination(null);
    emptyMessage.setMessageType(null);
    emptyMessage.setMessageID(null);
    emptyMessage.setCorrelationID(null);
    emptyMessage.setTimestamp(null);
    emptyMessage.setBody(null);

    // Assert
    assertNull(emptyMessage.getSource());
    assertNull(emptyMessage.getDestination());
    assertNull(emptyMessage.getMessageType());
    assertNull(emptyMessage.getMessageID());
    assertNull(emptyMessage.getCorrelationID());
    assertNull(emptyMessage.getTimestamp());
    assertNull(emptyMessage.getBody());
  }

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
    assertNull(msg.getBody(), "Body should accept null since no validation exists");
  }

  @Test
  void constructor_Negative_AllowsNullStrings() {
    // Arrange

    // Act
    Message msg = new Message(
            null,   // source
            null,   // destination
            null,   // type
            null,   // messageID
            null,   // correlationID
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

  @Test
  void setMessageID_Negative_AllowsEmptyString() {
    // Arrange

    // Act
    emptyMessage.setMessageID("");

    // Assert
    assertEquals("", emptyMessage.getMessageID());
  }

  @Test
  void setTimestamp_Negative_AllowsNullTimestamp() {
    // Arrange

    // Act
    emptyMessage.setTimestamp(null);

    // Assert
    assertNull(emptyMessage.getTimestamp());
  }
}
