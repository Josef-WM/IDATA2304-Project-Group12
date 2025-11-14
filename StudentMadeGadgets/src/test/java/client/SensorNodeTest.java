package client;

import actuator.Actuator;
import greenhouse.Greenhouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sensor.Sensor;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the SensorNode class.
 *
 * <p>Uses small inner test implementations of Sensor and Actuator
 * to verify SensorNode behavior without external mocking libraries.</p>
 */
class SensorNodeTest {

  private SensorNode node;

  /**
   * Simple test implementation of Sensor.
   */
  private static class TestSensor implements Sensor {
    private final String id;
    private final double value;

    TestSensor(String id, double value) {
      this.id = id;
      this.value = value;
    }

    @Override
    public String getID() {
      return id;
    }

    @Override
    public String getType() {
      return "TestSensor";
    }

    @Override
    public String getUnit() {
      return "units";
    }

    @Override
    public double read() {
      return value;
    }
  }

  /**
   * Test implementation of Actuator.
   */
  private static class TestActuator implements Actuator {
    private final String id;
    private boolean on = false;
    private int power = 0;

    TestActuator(String id) {
      this.id = id;
    }

    @Override
    public String getID() {
      return id;
    }

    @Override
    public String getType() {
      return "TestActuator";
    }

    @Override
    public boolean isOn() {
      return on;
    }

    @Override
    public boolean toggle() {
      on = !on;
      return on;
    }

    @Override
    public void setPower(int power) {
      this.power = power;
    }

    /**
     * Helper for tests to read back power level.
     */
    public int getPower() {
      return power;
    }
  }

  @BeforeEach
  void setUp() {
    // Arrange
    node = new SensorNode(new Greenhouse("TestHouse"));
  }

  @Test
  void addSensor_Positive_CanRetrieveById() {
    // Arrange
    Sensor sensor = new TestSensor("s1", 42.0);

    // Act
    node.addSensorToNode(sensor);
    Sensor result = node.getSensor("s1");

    // Assert
    assertEquals(sensor, result);
  }

  @Test
  void removeSensor_Positive_RemovesSensorFromNode() {
    // Arrange
    Sensor sensor = new TestSensor("s1", 10.0);
    node.addSensorToNode(sensor);

    // Act
    node.removeSensorFromNode(sensor);
    Sensor result = node.getSensor("s1");

    // Assert
    assertNull(result);
  }

  @Test
  void getSensor_Negative_UnknownIdReturnsNull() {
    // Arrange

    // Act
    Sensor result = node.getSensor("unknown");

    // Assert
    assertNull(result, "Unknown sensor ID should return null");
  }

  @Test
  void addActuator_Positive_CanRetrieveById() {
    // Arrange
    TestActuator actuator = new TestActuator("a1");

    // Act
    node.addActuatorToNode(actuator);
    Actuator result = node.getActuator("a1");

    // Assert
    assertEquals(actuator, result);
  }

  @Test
  void removeActuator_Positive_RemovesActuatorFromNode() {
    // Arrange
    TestActuator actuator = new TestActuator("a1");
    node.addActuatorToNode(actuator);

    // Act
    node.removeActuatorFromNode(actuator);
    Actuator result = node.getActuator("a1");

    // Assert
    assertNull(result);
  }

  @Test
  void getActuator_Negative_UnknownIdReturnsNull() {
    // Arrange

    // Act
    Actuator result = node.getActuator("doesNotExist");

    // Assert
    assertNull(result, "Unknown actuator ID should return null");
  }

  @Test
  void toggleActuator_Positive_TogglesOnAndOff() {
    // Arrange
    TestActuator actuator = new TestActuator("a1");
    node.addActuatorToNode(actuator);

    // Act
    boolean firstToggleState = node.toggleActuator("a1");
    boolean secondToggleState = node.toggleActuator("a1");

    // Assert
    assertTrue(firstToggleState, "First toggle should turn the actuator on");
    assertFalse(secondToggleState, "Second toggle should turn the actuator off");
    assertFalse(actuator.isOn(), "Actuator should be off after two toggles");
  }

  @Test
  void toggleActuator_Negative_UnknownIdThrowsNullPointerException() {
    // Arrange

    // Act & Assert
    assertThrows(NullPointerException.class,
            () -> node.toggleActuator("unknown"),
            "Toggling unknown actuator ID currently results in a NullPointerException");
  }

  @Test
  void setActuatorPower_Positive_UpdatesActuatorPower() {
    // Arrange
    TestActuator actuator = new TestActuator("a1");
    node.addActuatorToNode(actuator);

    // Act
    node.setActuatorPower("a1", 75);

    // Assert
    assertEquals(75, actuator.getPower());
  }

  @Test
  void setActuatorPower_Negative_UnknownIdThrowsNullPointerException() {
    // Arrange

    // Act & Assert
    assertThrows(NullPointerException.class,
            () -> node.setActuatorPower("unknown", 50),
            "Setting power on an unknown actuator ID currently throws NullPointerException");
  }
}
