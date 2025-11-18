package sensor;

import greenhouse.Greenhouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <body>
 *   <h1>Tests the class TemperatureSensor</h1>
 *   <p>The following is tested:</p>
 *
 *   <h2>Positive tests:</h2>
 *   <ul>
 *     <li>\`getID()\` returns the constructor-provided sensor id</li>
 *     <li>\`getType()\` returns "Temperature"</li>
 *     <li>\`getUnit()\` returns "Celsius"</li>
 *     <li>\`read()\` returns the current temperature from the associated \`Greenhouse\`</li>
 *   </ul>
 *
 *   <h2>Negative tests:</h2>
 *   <ul>
 *     <li>\`read()\` correctly handles low negative temperatures reported by the \`Greenhouse\`</li>
 *   </ul>
 *
 *   <p>Note: Tests use reflection to set the private temperature field on \`Greenhouse\`
 *   to keep execution deterministic and independent of timer-based updates.</p>
 * </body>
 * </html>
 */
class TemperatureSensorTest {

  private Greenhouse greenhouse;
  private TemperatureSensor sensor;

  @BeforeEach
  void setUp() {
    // Arrange
    greenhouse = new Greenhouse("TestHouse");
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

  /**
   * Tests that getID() returns the correct constructor value.
   */
  @Test
  void getId_Positive_ReturnsConstructorValue() {
    // Arrange done in setUp()

    // Act
    String id = sensor.getID();

    // Assert
    assertEquals("temp1", id);
  }

  /**
   * Tests that getType() returns "Temperature".
   */
  @Test
  void getType_Positive_ReturnsTemperature() {
    // Arrange

    // Act
    String type = sensor.getType();

    // Assert
    assertEquals("Temperature", type);
  }

  /**
   * Tests that getUnit() returns "Celsius".
   */
  @Test
  void getUnit_Positive_ReturnsCelsius() {
    // Arrange

    // Act
    String unit = sensor.getUnit();

    // Assert
    assertEquals("Celsius", unit);
  }

  /**
   * Tests that read() returns the current greenhouse temperature.
   */
  @Test
  void read_Positive_ReturnsGreenhouseTemperature() {
    // Arrange
    setTemperature(22.5);

    // Act
    double value = sensor.read();

    // Assert
    assertEquals(22.5, value, 0.0001);
  }

  /**
   * Tests that read() handles negative temperature values.
   */
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
