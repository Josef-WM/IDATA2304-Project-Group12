package sensor;

import greenhouse.Greenhouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TemperatureSensor.
 *
 * <p>Uses reflection to set the internal temperature field in Greenhouse
 * to keep tests deterministic and independent of the timer-based updates.</p>
 */
class TemperatureSensorTest {

  private Greenhouse greenhouse;
  private TemperatureSensor sensor;

  @BeforeEach
  void setUp() {
    // Arrange
    greenhouse = new Greenhouse(1,"TestHouse");
    sensor = new TemperatureSensor("temp1", greenhouse);
  }

  /**
   * Helper method to set the temperature field on Greenhouse via reflection.
   *
   * @param value temperature value to inject
   */
  private void setTemperature(double value) {
    try {
      Field field = Greenhouse.class.getDeclaredField("temperature");
      field.setAccessible(true);
      field.setDouble(greenhouse, value);
    } catch (Exception e) {
      fail("Failed to set temperature via reflection: " + e.getMessage());
    }
  }

  @Test
  void getId_Positive_ReturnsConstructorValue() {
    // Arrange done in setUp()

    // Act
    String id = sensor.getID();

    // Assert
    assertEquals("temp1", id);
  }

  @Test
  void getType_Positive_ReturnsTemperature() {
    // Arrange

    // Act
    String type = sensor.getType();

    // Assert
    assertEquals("Temperature", type);
  }

  @Test
  void getUnit_Positive_ReturnsCelsius() {
    // Arrange

    // Act
    String unit = sensor.getUnit();

    // Assert
    assertEquals("Celsius", unit);
  }

  @Test
  void read_Positive_ReturnsGreenhouseTemperature() {
    // Arrange
    setTemperature(22.5);

    // Act
    double value = sensor.read();

    // Assert
    assertEquals(22.5, value, 0.0001);
  }

  @Test
  void read_Negative_HandlesLowNegativeTemperature() {
    // Arrange
    setTemperature(-3.7);

    // Act
    double value = sensor.read();

    // Assert
    assertEquals(-3.7, value, 0.0001,
            "Sensor should still return negative values if greenhouse is below 0°C");
  }
}
