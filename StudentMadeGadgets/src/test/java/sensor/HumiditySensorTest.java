package sensor;

import greenhouse.Greenhouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link HumiditySensor} class.
 *
 * <p>Tests include both positive and negative scenarios, validating
 * that the sensor correctly reports humidity values from the
 * {@link Greenhouse}. Timer-based updates are disabled using
 * reflection to ensure stable behavior during tests.</p>
 */
class HumiditySensorTest {

  private Greenhouse greenhouse;
  private HumiditySensor sensor;

  /**
   * Sets up a deterministic Greenhouse instance and disables
   * its internal timer before each test.
   */
  @BeforeEach
  void setUp() {
    greenhouse = new Greenhouse("TestHouse");
    disableTimer();
    sensor = new HumiditySensor();
  }

  /**
   * Helper method to disable the automatic Greenhouse timer.
   * This prevents random environmental changes during tests.
   */
  private void disableTimer() {
    try {
      Field timerField = Greenhouse.class.getDeclaredField("timer");
      timerField.setAccessible(true);
      Object timer = timerField.get(greenhouse);
      timer.getClass().getMethod("cancel").invoke(timer);
    } catch (Exception e) {
      fail("Failed to disable greenhouse timer: " + e.getMessage());
    }
  }

  /**
   * Helper method to set the private 'humidity' field on Greenhouse.
   *
   * @param value the humidity value to inject (0–100)
   */
  private void setHumidity(int value) {
    try {
      Field field = Greenhouse.class.getDeclaredField("humidity");
      field.setAccessible(true);
      field.setInt(greenhouse, value);
    } catch (Exception e) {
      fail("Failed to set humidity via reflection: " + e.getMessage());
    }
  }

  /**
   * Tests that getType() returns "Humidity".
   * Expected outcome: exactly "Humidity".
   */
  @Test
  void getType_Positive_ReturnsHumidity() {
    // Arrange done in setUp()

    // Act
    String type = sensor.getType();

    // Assert
    assertEquals("Humidity", type);
  }

  /**
   * Tests that getUnit() returns "Percentage".
   * Expected outcome: exactly "Percentage".
   */
  @Test
  void getUnit_Positive_ReturnsPercentage() {
    // Arrange done in setUp()

    // Act
    String unit = sensor.getUnit();

    // Assert
    assertEquals("Percentage", unit);
  }

  /**
   * Tests that setID() and getID() correctly store and return the sensor ID.
   */
  @Test
  void setAndGetID_Positive_StoresIDCorrectly() {
    // Arrange
    sensor.setID("Humidity-1");

    // Act
    String id = sensor.getID();

    // Assert
    assertEquals("Humidity-1", id);
  }

  /**
   * Tests that read() returns the current humidity from the Greenhouse.
   * Expected outcome: read() exactly matches the injected value.
   */
  @Test
  void read_Positive_ReturnsGreenhouseHumidity() {
    // Arrange
    setHumidity(77);

    // Act
    double value = sensor.read(greenhouse);

    // Assert
    assertEquals(77.0, value);
  }

  /**
   * Tests that read() handles the minimum valid humidity (0%).
   * Expected outcome: read() = 0.
   */
  @Test
  void read_Negative_HandlesZeroHumidity() {
    // Arrange
    setHumidity(0);

    // Act
    double value = sensor.read(greenhouse);

    // Assert
    assertEquals(0.0, value);
  }

  /**
   * Tests that read() handles the maximum valid humidity (100%).
   * Expected outcome: read() = 100.
   */
  @Test
  void read_Negative_HandlesMaxHumidity() {
    // Arrange
    setHumidity(100);

    // Act
    double value = sensor.read(greenhouse);

    // Assert
    assertEquals(100.0, value);
  }

  /**
   * Tests that getID() returns null when no ID has been set.
   * Expected outcome: null.
   */
  @Test
  void getID_Negative_ReturnsNullWhenUnset() {
    // Arrange done in setUp()

    // Act
    String id = sensor.getID();

    // Assert
    assertNull(id);
  }
}
