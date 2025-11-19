package sensor;

import greenhouse.Greenhouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link LightSensor} class.
 *
 * <p>Tests cover both positive and negative scenarios using
 * the Arrange–Act–Assert pattern. Timer-based updates in
 * {@link Greenhouse} are disabled via reflection to ensure
 * deterministic test behavior.</p>
 */
class LightSensorTest {

  private Greenhouse greenhouse;
  private LightSensor sensor;

  /**
   * Sets up a deterministic Greenhouse instance and disables
   * its internal timer before each test.
   */
  @BeforeEach
  void setUp() {
    greenhouse = new Greenhouse("TestHouse");
    disableTimer();
    sensor = new LightSensor(greenhouse);
  }

  /**
   * Helper method to disable the Greenhouse timer so light values
   * remain stable during testing.
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
   * Helper method to set the private 'light' field in Greenhouse.
   *
   * @param value the light value to inject
   */
  private void setLight(int value) {
    try {
      Field field = Greenhouse.class.getDeclaredField("light");
      field.setAccessible(true);
      field.setInt(greenhouse, value);
    } catch (Exception e) {
      fail("Failed to set light via reflection: " + e.getMessage());
    }
  }

  /**
   * Tests that getType() returns "Light".
   * Expected outcome: exactly "Light".
   */
  @Test
  void getType_Positive_ReturnsLight() {
    // Arrange done in setUp()

    // Act
    String type = sensor.getType();

    // Assert
    assertEquals("Light", type);
  }

  /**
   * Tests that getUnit() returns "Lux".
   * Expected outcome: exactly "Lux".
   */
  @Test
  void getUnit_Positive_ReturnsLux() {
    // Arrange done in setUp()

    // Act
    String unit = sensor.getUnit();

    // Assert
    assertEquals("Lux", unit);
  }

  /**
   * Tests setting and getting the ID.
   * Expected outcome: sensor returns same ID after setID().
   */
  @Test
  void setAndGetID_Positive_StoresIDCorrectly() {
    // Arrange
    sensor.setID("Light-1");

    // Act
    String id = sensor.getID();

    // Assert
    assertEquals("Light-1", id);
  }

  /**
   * Tests that read() returns the current light value from the Greenhouse.
   * Expected outcome: read() matches injected value exactly.
   */
  @Test
  void read_Positive_ReturnsGreenhouseLightValue() {
    // Arrange
    setLight(1234);

    // Act
    double value = sensor.read();

    // Assert
    assertEquals(1234.0, value);
  }

  /**
   * Tests that read() handles very low (0) light values.
   * Expected outcome: read() = 0.
   */
  @Test
  void read_Negative_HandlesZeroLight() {
    // Arrange
    setLight(0);

    // Act
    double value = sensor.read();

    // Assert
    assertEquals(0.0, value);
  }

  /**
   * Tests that read() handles extremely high light values.
   * This checks robustness, not real-world constraints.
   * Expected outcome: read() returns the injected value correctly.
   */
  @Test
  void read_Negative_HandlesHighLightValue() {
    // Arrange
    setLight(999999);

    // Act
    double value = sensor.read();

    // Assert
    assertEquals(999999.0, value);
  }

  /**
   * Tests getID() returns null when no ID has been set.
   * Expected outcome: null (because LightSensor constructor does not assign one).
   */
  @Test
  void getID_Negative_ReturnsNullIfNotSet() {
    // Arrange done in setUp()

    // Act
    String id = sensor.getID();

    // Assert
    assertNull(id);
  }
}
