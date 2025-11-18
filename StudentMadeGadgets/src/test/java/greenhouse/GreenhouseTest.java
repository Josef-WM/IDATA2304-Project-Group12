package greenhouse;

import client.SensorNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Greenhouse} class.
 *
 * <p>This test suite validates both positive and negative scenarios for the
 * greenhouse environment model. Automatic updates from the internal timer are
 * disabled during testing using reflection to ensure deterministic results.</p>
 *
 * <p>The AAA (Arrange–Act–Assert) test structure is used consistently.</p>
 */
class GreenhouseTest {

  private Greenhouse greenhouse;

  /**
   * Initializes a new Greenhouse instance before each test and disables its timer.
   */
  @BeforeEach
  void setUp() {
    greenhouse = new Greenhouse("TestHouse");
    disableTimer();
  }

  /**
   * Disables the internal timer to prevent random environmental updates.
   */
  private void disableTimer() {
    try {
      Field timerField = Greenhouse.class.getDeclaredField("timer");
      timerField.setAccessible(true);
      Timer timer = (Timer) timerField.get(greenhouse);
      timer.cancel();
    } catch (Exception e) {
      fail("Failed to cancel greenhouse timer: " + e.getMessage());
    }
  }

  /**
   * Helper method: sets a private double field (temperature).
   */
  private void setDoubleField(String fieldName, double value) {
    try {
      Field field = Greenhouse.class.getDeclaredField(fieldName);
      field.setAccessible(true);
      field.setDouble(greenhouse, value);
    } catch (Exception e) {
      fail("Failed to set field: " + e.getMessage());
    }
  }

  /**
   * Helper method: sets a private int field (humidity, light).
   */
  private void setIntField(String fieldName, int value) {
    try {
      Field field = Greenhouse.class.getDeclaredField(fieldName);
      field.setAccessible(true);
      field.setInt(greenhouse, value);
    } catch (Exception e) {
      fail("Failed to set field: " + e.getMessage());
    }
  }

  /**
   * Tests that the constructor correctly initializes default greenhouse values.
   * Expected outcome: initial temperature = 14, humidity = 60, light = 1000.
   */
  @Test
  void constructor_Positive_SetsInitialValuesCorrectly() {
    // Arrange done in setUp()

    // Act
    double temp = greenhouse.getTemperature();
    int humidity = greenhouse.getHumidity();
    int light = greenhouse.getLight();
    String name = greenhouse.getGreenhouseName();

    // Assert
    assertEquals(14.0, temp);
    assertEquals(60, humidity);
    assertEquals(1000, light);
    assertEquals("TestHouse", name);
  }

  /**
   * Tests that setting a greenhouse ID correctly updates the internal field.
   * Expected outcome: ID is stored and returned.
   */
  @Test
  void setGreenhouseId_Positive_UpdatesIdCorrectly() {
    // Arrange

    // Act
    greenhouse.setGreenhouseId(42);

    // Assert
    assertEquals(42, greenhouse.getGreenhouseId());
  }

  /**
   * Tests that changing the temperature with a positive adjustment increases the value.
   * Expected outcome: temperature increases by the given delta.
   */
  @Test
  void changeTemperature_Positive_IncreasesTemperature() {
    // Arrange
    setDoubleField("temperature", 10.0);

    // Act
    greenhouse.changeTemperature(5.0);

    // Assert
    assertEquals(15.0, greenhouse.getTemperature());
  }

  /**
   * Tests that changing the temperature with a negative adjustment decreases the value.
   * Expected outcome: temperature decreases by the given delta.
   */
  @Test
  void changeTemperature_Positive_DecreasesTemperature() {
    // Arrange
    setDoubleField("temperature", 20.0);

    // Act
    greenhouse.changeTemperature(-3.0);

    // Assert
    assertEquals(17.0, greenhouse.getTemperature());
  }

  /**
   * Tests that humidity increases correctly when within bounds.
   * Expected outcome: humidity + change <= 100.
   */
  @Test
  void changeHumidity_Positive_IncreasesWithinBounds() {
    // Arrange
    setIntField("humidity", 50);

    // Act
    greenhouse.changeHumidity(10);

    // Assert
    assertEquals(60, greenhouse.getHumidity());
  }

  /**
   * Tests that light level increases correctly.
   * Expected outcome: light + delta is applied.
   */
  @Test
  void changeLight_Positive_IncreasesLightLevel() {
    // Arrange
    setIntField("light", 1000);

    // Act
    greenhouse.changeLight(200);

    // Assert
    assertEquals(1200, greenhouse.getLight());
  }

  /**
   * Tests that humidity does not exceed the upper bound (100%) even when a large value is added.
   * Expected outcome: humidity <= 100 after update.
   */
  @Test
  void changeHumidity_Negative_DoesNotExceedUpperBound() {
    // Arrange
    setIntField("humidity", 95);

    // Act
    greenhouse.changeHumidity(50);

    // Assert
    assertTrue(greenhouse.getHumidity() <= 100,
            "Humidity should never exceed 100%");
  }

  /**
   * Tests that humidity does not drop below 0% even when a large negative value is applied.
   * Expected outcome: humidity >= 0 after update.
   */
  @Test
  void changeHumidity_Negative_DoesNotDropBelowZero() {
    // Arrange
    setIntField("humidity", 5);

    // Act
    greenhouse.changeHumidity(-50);

    // Assert
    assertTrue(greenhouse.getHumidity() >= 0,
            "Humidity should never fall below 0%");
  }

  /**
   * Tests that negative changes to light reduce the amount safely.
   * Expected outcome: light decreases by delta.
   */
  @Test
  void changeLight_Negative_DecreasesLightLevel() {
    // Arrange
    setIntField("light", 500);

    // Act
    greenhouse.changeLight(-200);

    // Assert
    assertEquals(300, greenhouse.getLight());
  }

  /**
   * Tests that calling changeTemperature with a huge negative delta does not break behavior.
   * Expected outcome: temperature simply decreases — no exception thrown.
   */
  @Test
  void changeTemperature_Negative_LargeDecreaseDoesNotThrow() {
    // Arrange
    setDoubleField("temperature", 10.0);

    // Act & Assert
    assertDoesNotThrow(() -> greenhouse.changeTemperature(-100),
            "Large negative temperature changes should not throw an exception");
  }

  /**
   * Tests that setting the greenhouse name to null does not throw an exception.
   * Expected outcome: name becomes null.
   */
  @Test
  void setGreenhouseName_Negative_AllowsNullName() {
    // Arrange

    // Act
    greenhouse.setGreenhouseName(null);

    // Assert
    assertNull(greenhouse.getGreenhouseName());
  }
}

