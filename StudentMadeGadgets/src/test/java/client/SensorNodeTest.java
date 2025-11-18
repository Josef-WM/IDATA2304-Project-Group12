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
 * Tests the class SensorNode for sensor and actuator management.
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
   * Tests adding a Sensor and retrieving it by ID.
   * Expected outcome: getSensor() returns the previously added Sensor.
   */
  @Test
  void addSensor_Positive_CanRetrieveById() {
    Sensor sensor = new SensorTest("s1", 42.0);
    node.addSensorToNode(sensor);
    Sensor result = node.getSensor("s1");
    assertEquals(sensor, result);
  }

  /**
   * Tests removing a Sensor makes subsequent lookup return null.
   * Expected outcome: getSensor() returns null after removal.
   */
  @Test
  void removeSensor_Positive_SensorIsRemovedFromNode() {
    Sensor sensor = new SensorTest("s1", 10.0);
    node.addSensorToNode(sensor);
    node.removeSensorFromNode(sensor);
    Sensor result = node.getSensor("s1");
    assertNull(result, "Sensor should be null after being removed");
  }

  /**
   * Tests adding an Actuator and retrieving it by ID.
   * Expected outcome: getActuator() returns the previously added Actuator.
   */
  @Test
  void addActuator_Positive_CanRetrieveById() {
    ActuatorTest actuator = new ActuatorTest("a1");
    node.addActuatorToNode(actuator);
    Actuator result = node.getActuator("a1");
    assertEquals(actuator, result);
  }

  /**
   * Tests removing an Actuator makes subsequent lookup return null.
   * Expected outcome: getActuator() returns null after removal.
   */
  @Test
  void removeActuator_Positive_ActuatorIsRemovedFromNode() {
    ActuatorTest actuator = new ActuatorTest("a1");
    node.addActuatorToNode(actuator);
    node.removeActuatorFromNode(actuator);
    Actuator result = node.getActuator("a1");
    assertNull(result, "Actuator should be null after being removed");
  }

  /**
   * Tests toggling an Actuator twice toggles ON then OFF.
   * Expected outcome: first toggle returns true, second returns false and actuator is off.
   */
  @Test
  void toggleActuator_Positive_TogglesOnAndOff() {
    ActuatorTest actuator = new ActuatorTest("a1");
    node.addActuatorToNode(actuator);
    boolean firstToggle = node.toggleActuator("a1", greenhouse);
    boolean secondToggle = node.toggleActuator("a1", greenhouse);
    assertTrue(firstToggle, "First toggle should turn actuator ON");
    assertFalse(secondToggle, "Second toggle should turn actuator OFF");
    assertFalse(actuator.isOn(), "Actuator should be OFF after two toggles");
  }

  /**
   * Tests setting actuator power updates the actuator.
   * Expected outcome: actuator reports the new power value.
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
   * Expected outcome: getSensor() returns null for unknown id.
   */
  @Test
  void getSensor_Negative_UnknownIdReturnsNull() {
    Sensor result = node.getSensor("unknown");
    assertNull(result, "Unknown sensor ID should return null");
  }

  /**
   * Tests getting an unknown Actuator returns null.
   * Expected outcome: getActuator() returns null for unknown id.
   */
  @Test
  void getActuator_Negative_UnknownIdReturnsNull() {
    Actuator result = node.getActuator("unknown");
    assertNull(result, "Unknown actuator ID should return null");
  }

  /**
   * Tests removing an unknown Sensor does not throw and leaves it absent.
   * Expected outcome: no exception and getSensor() remains null.
   */
  @Test
  void removeSensor_Negative_RemovingUnknownSensorDoesNotThrow() {
    Sensor sensor = new SensorTest("s999", 0.0);
    node.removeSensorFromNode(sensor);
    assertNull(node.getSensor("s999"),
            "Removing a sensor that was never added should still leave it as null");
  }

  /**
   * Tests removing an unknown Actuator does not throw and leaves it absent.
   * Expected outcome: no exception and getActuator() remains null.
   */
  @Test
  void removeActuator_Negative_RemovingUnknownActuatorDoesNotThrow() {
    ActuatorTest actuator = new ActuatorTest("a999");
    node.removeActuatorFromNode(actuator);
    assertNull(node.getActuator("a999"),
            "Removing an actuator that was never added should still leave it as null");
  }

  /**
   * Tests toggling an unknown Actuator ID throws NullPointerException.
   * Expected outcome: NullPointerException is thrown.
   */
  @Test
  void toggleActuator_Negative_UnknownIdThrowsNullPointerException() {
    assertThrows(NullPointerException.class,
            () -> node.toggleActuator("unknown"),
            "toggleActuator on unknown ID currently results in NullPointerException");
  }

  /**
   * Tests setting power on an unknown Actuator ID throws NullPointerException.
   * Expected outcome: NullPointerException is thrown.
   */
  @Test
  void setActuatorPower_Negative_UnknownIdThrowsNullPointerException() {
    assertThrows(NullPointerException.class,
            () -> node.setActuatorPower("unknown", 50),
            "setActuatorPower on unknown ID currently results in NullPointerException");
  }
}
