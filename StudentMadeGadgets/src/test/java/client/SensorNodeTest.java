package client;

import actuator.Actuator;
import greenhouse.Greenhouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sensor.Sensor;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link SensorNode} class.
 *
 * <p>Tests both positive (expected) and negative (edge/error) scenarios
 * using the Arrange–Act–Assert pattern.</p>
 */
class SensorNodeTest {

  private SensorNode node;

  /**
   * Simple test implementation of {@link Sensor} for unit testing.
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
   * Simple test implementation of {@link Actuator} for unit testing.
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
     * Helper method for assertions on power level.
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

  // --------------------------------------------------------------
  //   POSITIVE TESTS
  // --------------------------------------------------------------

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
  void removeSensor_Positive_SensorIsRemovedFromNode() {
    // Arrange
    Sensor sensor = new TestSensor("s1", 10.0);
    node.addSensorToNode(sensor);

    // Act
    node.removeSensorFromNode(sensor);
    Sensor result = node.getSensor("s1");

    // Assert
    assertNull(result, "Sensor should be null after being removed");
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
  void removeActuator_Positive_ActuatorIsRemovedFromNode() {
    // Arrange
    TestActuator actuator = new TestActuator("a1");
    node.addActuatorToNode(actuator);

    // Act
    node.removeActuatorFromNode(actuator);
    Actuator result = node.getActuator("a1");

    // Assert
    assertNull(result, "Actuator should be null after being removed");
  }

  @Test
  void toggleActuator_Positive_TogglesOnAndOff() {
    // Arrange
    TestActuator actuator = new TestActuator("a1");
    node.addActuatorToNode(actuator);

    // Act
    boolean firstToggle = node.toggleActuator("a1");
    boolean secondToggle = node.toggleActuator("a1");

    // Assert
    assertTrue(firstToggle, "First toggle should turn actuator ON");
    assertFalse(secondToggle, "Second toggle should turn actuator OFF");
    assertFalse(actuator.isOn(), "Actuator should be OFF after two toggles");
  }

  @Test
  void setActuatorPower_Positive_UpdatesPowerOnActuator() {
    // Arrange
    TestActuator actuator = new TestActuator("a1");
    node.addActuatorToNode(actuator);

    // Act
    node.setActuatorPower("a1", 75);

    // Assert
    assertEquals(75, actuator.getPower());
  }

  // --------------------------------------------------------------
  //   NEGATIVE TESTS
  // --------------------------------------------------------------

  @Test
  void getSensor_Negative_UnknownIdReturnsNull() {
    // Arrange

    // Act
    Sensor result = node.getSensor("unknown");

    // Assert
    assertNull(result, "Unknown sensor ID should return null");
  }

  @Test
  void getActuator_Negative_UnknownIdReturnsNull() {
    // Arrange

    // Act
    Actuator result = node.getActuator("unknown");

    // Assert
    assertNull(result, "Unknown actuator ID should return null");
  }

  @Test
  void removeSensor_Negative_RemovingUnknownSensorDoesNotThrow() {
    // Arrange
    Sensor sensor = new TestSensor("s999", 0.0);

    // Act (no exception should be thrown)
    node.removeSensorFromNode(sensor);

    // Assert
    assertNull(node.getSensor("s999"),
            "Removing a sensor that was never added should still leave it as null");
  }

  @Test
  void removeActuator_Negative_RemovingUnknownActuatorDoesNotThrow() {
    // Arrange
    TestActuator actuator = new TestActuator("a999");

    // Act (no exception should be thrown)
    node.removeActuatorFromNode(actuator);

    // Assert
    assertNull(node.getActuator("a999"),
            "Removing an actuator that was never added should still leave it as null");
  }

  @Test
  void toggleActuator_Negative_UnknownIdThrowsNullPointerException() {
    // Arrange

    // Act & Assert
    assertThrows(NullPointerException.class,
            () -> node.toggleActuator("unknown"),
            "toggleActuator on unknown ID currently results in NullPointerException");
  }

  @Test
  void setActuatorPower_Negative_UnknownIdThrowsNullPointerException() {
    // Arrange

    // Act & Assert
    assertThrows(NullPointerException.class,
            () -> node.setActuatorPower("unknown", 50),
            "setActuatorPower on unknown ID currently results in NullPointerException");
  }
}
