// Java
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
 *     <li>Adding and retrieving a Sensor by id</li>
 *     <li>Removing a Sensor makes it unreachable</li>
 *     <li>Adding and retrieving an Actuator by id</li>
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
   * Tests adding a Sensor and retrieving it by id.
   * Expected outcome: retrieval returns the same Sensor instance.
   */
  @Test
  void addSensor_Positive_CanRetrieveById() {
    Sensor sensor = new SensorTest("s1", 42.0);
    node.addSensorToNode(sensor);
    assertEquals(sensor, node.getSensor("s1"));
  }

  /**
   * Tests removing a Sensor makes subsequent lookup return null.
   * Expected outcome: getSensor returns null after removal.
   */
  @Test
  void removeSensor_Positive_SensorIsRemovedFromNode() {
    Sensor sensor = new SensorTest("s1", 10.0);
    node.addSensorToNode(sensor);
    node.removeSensorFromNode(sensor);
    assertNull(node.getSensor("s1"));
  }

  /**
   * Tests adding an Actuator and retrieving it by id.
   * Expected outcome: retrieval returns the same Actuator instance.
   */
  @Test
  void addActuator_Positive_CanRetrieveById() {
    ActuatorTest actuator = new ActuatorTest("a1");
    node.addActuatorToNode(actuator);
    assertEquals(actuator, node.getActuator("a1"));
  }

  /**
   * Tests removing an Actuator makes subsequent lookup return null.
   * Expected outcome: getActuator returns null after removal.
   */
  @Test
  void removeActuator_Positive_ActuatorIsRemovedFromNode() {
    ActuatorTest actuator = new ActuatorTest("a1");
    node.addActuatorToNode(actuator);
    node.removeActuatorFromNode(actuator);
    assertNull(node.getActuator("a1"));
  }

  /**
   * Tests toggling an Actuator twice toggles ON then OFF.
   * Expected outcome: first toggle true, second false, actuator off.
   */
  @Test
  void toggleActuator_Positive_TogglesOnAndOff() {
    ActuatorTest actuator = new ActuatorTest("a1");
    node.addActuatorToNode(actuator);
    boolean first = node.toggleActuator("a1", greenhouse);
    boolean second = node.toggleActuator("a1", greenhouse);
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
    ActuatorTest actuator = new ActuatorTest("a1");
    node.addActuatorToNode(actuator);
    node.setActuatorPower("a1", 75);
    assertEquals(75, actuator.getPower());
  }

  /**
   * Tests getting an unknown Sensor returns null.
   * Expected outcome: null for unknown id.
   */
  @Test
  void getSensor_Negative_UnknownIdReturnsNull() {
    assertNull(node.getSensor("unknown"));
  }

  /**
   * Tests getting an unknown Actuator returns null.
   * Expected outcome: null for unknown id.
   */
  @Test
  void getActuator_Negative_UnknownIdReturnsNull() {
    assertNull(node.getActuator("unknown"));
  }

  /**
   * Tests removing an unknown Sensor does nothing.
   * Expected outcome: still null, no exception.
   */
  @Test
  void removeSensor_Negative_RemovingUnknownSensorDoesNotThrow() {
    Sensor sensor = new SensorTest("s999", 0.0);
    node.removeSensorFromNode(sensor);
    assertNull(node.getSensor("s999"));
  }

  /**
   * Tests removing an unknown Actuator does nothing.
   * Expected outcome: still null, no exception.
   */
  @Test
  void removeActuator_Negative_RemovingUnknownActuatorDoesNotThrow() {
    ActuatorTest actuator = new ActuatorTest("a999");
    node.removeActuatorFromNode(actuator);
    assertNull(node.getActuator("a999"));
  }

  /**
   * Tests toggling an unknown Actuator id throws NullPointerException.
   * Expected outcome: NullPointerException.
   */
  @Test
  void toggleActuator_Negative_UnknownIdThrowsNullPointerException() {
    assertThrows(NullPointerException.class,
            () -> node.toggleActuator("unknown", greenhouse));
  }

  /**
   * Tests setting power on an unknown Actuator id throws NullPointerException.
   * Expected outcome: NullPointerException.
   */
  @Test
  void setActuatorPower_Negative_UnknownIdThrowsNullPointerException() {
    assertThrows(NullPointerException.class,
            () -> node.setActuatorPower("unknown", 50));
  }
}
