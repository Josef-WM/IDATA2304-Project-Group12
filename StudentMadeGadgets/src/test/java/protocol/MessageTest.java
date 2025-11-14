package protocol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Message} class.
 *
 * <p>This class verifies that:</p>
 * <ul>
 *     <li>Constructors correctly assign all fields</li>
 *     <li>Getters return the expected values</li>
 *     <li>Setters correctly update values</li>
 * </ul>
 */
class MessageTest {

  private Message message;

  /**
   * Creates a fresh {@link Message} instance before each test.
   */
  @BeforeEach
  void setUp() {
    message = new Message();
  }

  /**
   * Tests the constructor that includes correlationID.
   */
  @Test
  void testFullConstructor() {
    Message msg = new Message(
            "nodeA",
            "nodeB",
            "sensor_data",
            "msg123",
            "corr456",
            1000L,
            "payload"
    );

    assertEquals("nodeA", msg.getSource());
    assertEquals("nodeB", msg.getDestination());
    assertEquals("sensor_data", msg.getMessageType());
    assertEquals("msg123", msg.getMessageID());
    assertEquals("corr456", msg.getCorrelationID());
    assertEquals(1000L, msg.getTimestamp());
    assertEquals("payload", msg.getBody());
  }

  /**
   * Tests the constructor without correlationID.
   */
  @Test
  void testConstructorWithoutCorrelationID() {
    Message msg = new Message(
            "nodeA",
            "nodeB",
            "command",
            "msg789",
            2000L,
            42
    );

    assertEquals("nodeA", msg.getSource());
    assertEquals("nodeB", msg.getDestination());
    assertEquals("command", msg.getMessageType());
    assertEquals("msg789", msg.getMessageID());
    assertEquals(2000L, msg.getTimestamp());
    assertEquals(42, msg.getBody());
    assertNull(msg.getCorrelationID());
  }

  /**
   * Tests setter and getter for source.
   */
  @Test
  void testSetAndGetSource() {
    message.setSource("sensor1");
    assertEquals("sensor1", message.getSource());
  }

  /**
   * Tests setter and getter for destination.
   */
  @Test
  void testSetAndGetDestination() {
    message.setDestination("control1");
    assertEquals("control1", message.getDestination());
  }

  /**
   * Tests setter and getter for messageType.
   */
  @Test
  void testSetAndGetMessageType() {
    message.setMessageType("update");
    assertEquals("update", message.getMessageType());
  }

  /**
   * Tests setter and getter for messageID.
   */
  @Test
  void testSetAndGetMessageID() {
    message.setMessageID("GreenFN");
    assertEquals("GreenFN", message.getMessageID());
  }

  /**
   * Tests setter and getter for correlationID.
   */
  @Test
  void testSetAndGetCorrelationID() {
    message.setCorrelationID("corr001");
    assertEquals("corr001", message.getCorrelationID());
  }

  /**
   * Tests setter and getter for timestamp.
   */
  @Test
  void testSetAndGetTimestamp() {
    message.setTimestamp(67L);
    assertEquals(67L, message.getTimestamp());
  }

  /**
   * Tests setter and getter for body.
   */
  @Test
  void testSetAndGetBody() {
    message.setBody("data_payload");
    assertEquals("data_payload", message.getBody());
  }
}
