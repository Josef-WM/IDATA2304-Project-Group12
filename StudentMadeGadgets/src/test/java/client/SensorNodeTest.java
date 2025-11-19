package client;

import actuator.Actuator;
import actuator.ActuatorTest;
import greenhouse.Greenhouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sensor.Sensor;
import sensor.SensorTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <body>
 *   <h1>Tests the class SensorNode</h1>
 *   <p>The following functionality is covered.</p>
 *   <h2>Positive tests:</h2>
 *   <ul>
 *     <li>Adding a Sensor and retrieving it by the generated id</li>
 *     <li>Removing a Sensor makes it unreachable</li>
 *     <li>Adding an Actuator and retrieving it by the generated id</li>
 *     <li>Removing an Actuator makes it unreachable</li>
 *     <li>Toggling an Actuator twice turns it ON then OFF</li>
 *     <li>Setting actuator power updates its internal state</li>
 *   </ul>
 *   <h2>Negative tests:</h2>
 *   <ul>
 *     <li>Looking up unknown Sensor returns null</li>
 *     <li>Looking up unknown Actuator returns null</li>
 *     <li>Removing unknown Sensor is a no-op (no exception)</li>
 *     <li>Removing unknown Actuator is a no-op (no exception)</li>
 *     <li>Toggling unknown Actuator id throws NullPointerException</li>
 *     <li>Setting power on unknown Actuator id throws NullPointerException</li>
 *   </ul>
 * </body>
 */
class SensorNodeTest {

  private SensorNode node;
  private Greenhouse greenhouse;

  /**
   * Initializes a fresh SensorNode before each test.
   * Expected outcome: node is ready to accept sensors and actuators.
   */
  @BeforeEach
  void setUp() {
    node = new SensorNode();
    greenhouse = new Greenhouse("TestHouse");
  }

  /**
   * Tests adding a Sensor and retrieving it by the generated id.
   * Expected outcome: retrieval returns the same Sensor instance and
   *                   the id returned by addSensorToNode matches sensor.getID().
   */
  @Test
  void addSensor_Positive_CanRetrieveById() {
    // Arrange
    Sensor sensor = new SensorTest("s1", 42.0);

    // Act
    String id = node.addSensorToNode(sensor);
    Sensor result = node.getSensor(id);

    // Assert
    assertEquals(sensor, result);
    assertEquals(id, sensor.getID(), "Sensor id should be set to the generated id");
  }

  /**
   * Tests removing a Sensor makes subsequent lookup return null.
   * Expected outcome: getSensor returns null after removal when using
   *                   the generated id.
   */
  @Test
  void removeSensor_Positive_SensorIsRemovedFromNode() {
    // Arrange
    Sensor sensor = new SensorTest("s1", 10.0);
    String id = node.addSensorToNode(sensor);

    // Act
    node.removeSensorFromNode(sensor);
    Sensor result = node.getSensor(id);

    // Assert
    assertNull(result);
  }

  /**
   * Tests adding an Actuator and retrieving it by the generated id.
   * Expected outcome: retrieval returns the same Actuator instance and
   *                   the id returned by addActuatorToNode matches actuator.getID().
   */
  @Test
  void addActuator_Positive_CanRetrieveById() {
    // Arrange
    ActuatorTest actuator = new ActuatorTest("a1");

    // Act
    String id = node.addActuatorToNode(actuator);
    Actuator result = node.getActuator(id);

    // Assert
    assertEquals(actuator, result);
    assertEquals(id, actuator.getID(), "Actuator id should be set to the generated id");
  }

  /**
   * Tests removing an Actuator makes subsequent lookup return null.
   * Expected outcome: getActuator returns null after removal when using
   *                   the generated id.
   */
  @Test
  void removeActuator_Positive_ActuatorIsRemovedFromNode() {
    // Arrange
    ActuatorTest actuator = new ActuatorTest("a1");
    String id = node.addActuatorToNode(actuator);

    // Act
    node.removeActuatorFromNode(actuator);
    Actuator result = node.getActuator(id);

    // Assert
    assertNull(result);
  }

  /**
   * Tests toggling an Actuator twice toggles ON then OFF.
   * Expected outcome: first toggle true, second false, actuator off.
   */
  @Test
  void toggleActuator_Positive_TogglesOnAndOff() {
    // Arrange
    ActuatorTest actuator = new ActuatorTest("a1");
    String id = node.addActuatorToNode(actuator);

    // Act
    boolean first = node.toggleActuator(id, greenhouse);
    boolean second = node.toggleActuator(id, greenhouse);

    // Assert
    assertTrue(first);
    assertFalse(second);
    assertFalse(actuator.isOn());
  }

  /**
   * Tests setting actuator power updates the actuator.
   * Expected outcome: actuator reports new power value.
   */
  @Test
  void setActuatorPower_Positive_UpdatesPowerOnActuator() {
    // Arrange
    ActuatorTest actuator = new ActuatorTest("a1");
    String id = node.addActuatorToNode(actuator);

    // Act
    node.setActuatorPower(id, 75, greenhouse);

    // Assert
    assertEquals(75, actuator.getPower());
  }

  /**
   * Tests getting an unknown Sensor returns null.
   * Expected outcome: null for unknown id.
   */
  @Test
  void getSensor_Negative_UnknownIdReturnsNull() {
    // Arrange

    // Act
    Sensor result = node.getSensor("unknown");

    // Assert
    assertNull(result);
  }

  /**
   * Tests getting an unknown Actuator returns null.
   * Expected outcome: null for unknown id.
   */
  @Test
  void getActuator_Negative_UnknownIdReturnsNull() {
    // Arrange

    // Act
    Actuator result = node.getActuator("unknown");

    // Assert
    assertNull(result);
  }

  /**
   * Tests removing an unknown Sensor does nothing.
   * Expected outcome: still null, no exception.
   */
  @Test
  void removeSensor_Negative_RemovingUnknownSensorDoesNotThrow() {
    // Arrange
    Sensor sensor = new SensorTest("s999", 0.0);

    // Act (no exception expected)
    node.removeSensorFromNode(sensor);

    // Assert
    assertNull(node.getSensor(sensor.getID()));
  }

  /**
   * Tests removing an unknown Actuator does nothing.
   * Expected outcome: still null, no exception.
   */
  @Test
  void removeActuator_Negative_RemovingUnknownActuatorDoesNotThrow() {
    // Arrange
    ActuatorTest actuator = new ActuatorTest("a999");

    // Act (no exception expected)
    node.removeActuatorFromNode(actuator);

    // Assert
    assertNull(node.getActuator(actuator.getID()));
  }

  /**
   * Tests toggling an unknown Actuator id throws NullPointerException.
   * Expected outcome: NullPointerException.
   */
  @Test
  void toggleActuator_Negative_UnknownIdThrowsNullPointerException() {
    // Arrange

    // Act & Assert
    assertThrows(NullPointerException.class,
            () -> node.toggleActuator("unknown", greenhouse));
  }

  /**
   * Tests setting power on an unknown Actuator id throws NullPointerException.
   * Expected outcome: NullPointerException.
   */
  @Test
  void setActuatorPower_Negative_UnknownIdThrowsNullPointerException() {
    // Arrange

    // Act & Assert
    assertThrows(NullPointerException.class,
            () -> node.setActuatorPower("unknown", 50, greenhouse));
  }
}
