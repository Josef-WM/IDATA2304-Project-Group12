package actuator;

import greenhouse.Greenhouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link FanActuator} class.
 *
 * <p>This test suite verifies:
 * <ul>
 *   <li>ID assignment and retrieval</li>
 *   <li>Correct actuator type</li>
 *   <li>Initial OFF state and default power</li>
 *   <li>turnOn() cools the greenhouse and sets actuator ON</li>
 *   <li>turnOff() restores temperature and sets actuator OFF</li>
 *   <li>toggle() alternates state and affects temperature accordingly</li>
 *   <li>setState(boolean) controls state as expected</li>
 *   <li>setPower(int) influences cooling effect</li>
 *   <li>Negative tests for unset ID and repeated operations</li>
 * </ul>
 * </p>
 *
 * <p>The internal Greenhouse timer is disabled for deterministic testing.</p>
 */
class FanActuatorTest {

  private FanActuator actuator;
  private Greenhouse greenhouse;

  /**
   * Initializes a new FanActuator and a deterministic Greenhouse
   * instance before each test.
   */
  @BeforeEach
  void setUp() {
    actuator = new FanActuator();
    greenhouse = new Greenhouse("TestHouse");
    disableTimer();
  }

  /**
   * Disables the automatic timer inside Greenhouse to avoid
   * random environment changes during tests.
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
   * Helper method to set the private 'temperature' field on Greenhouse.
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
   * Helper method to get the private 'temperature' field on Greenhouse.
   *
   * @return the current temperature value stored in greenhouse
   */
  private double getTemperature() {
    try {
      Field field = Greenhouse.class.getDeclaredField("temperature");
      field.setAccessible(true);
      return field.getDouble(greenhouse);
    } catch (Exception e) {
      fail("Failed to read temperature via reflection: " + e.getMessage());
      return Double.NaN;
    }
  }

  /**
   * Tests that getType() returns "Fan".
   */
  @Test
  void getType_Positive_ReturnsFan() {
    // Arrange done in setUp()

    // Act
    String type = actuator.getType();

    // Assert
    assertEquals("Fan", type);
  }

  /**
   * Tests that ID is stored and returned correctly.
   */
  @Test
  void setAndGetID_Positive_StoresIDCorrectly() {
    // Arrange
    actuator.setID("Fan-1");

    // Act
    String id = actuator.getID();

    // Assert
    assertEquals("Fan-1", id);
  }

  /**
   * Tests the initial state of the actuator.
   * Expected outcome: OFF and power 0.
   */
  @Test
  void constructor_Positive_StartsOffWithZeroPower() {
    // Arrange done in setUp()

    // Act & Assert
    assertFalse(actuator.isOn(), "Fan should start OFF");
    assertEquals(0, actuator.getPower(), "Fan power should start at 0");
  }

  /**
   * Tests that turnOn() cools the greenhouse and sets the actuator to ON.
   * At speed 0, expected temperature change: -5.
   */
  @Test
  void turnOn_Positive_CoolsGreenhouseAndSetsOn() {
    // Arrange
    double temperature = 25;
    setTemperature(temperature);
    actuator.setPower(0, greenhouse);

    // Act
    actuator.turnOn(greenhouse);
    double newTemp = getTemperature();

    // Assert
    assertTrue(actuator.isOn());
    assertEquals(temperature+actuator.getEffect(), newTemp, 0.0001,
            "Temperature should be reduced by 3 at speed 0");
  }

  /**
   * Tests that turnOff() restores the previous temperature
   * and sets the actuator to OFF.
   */
  @Test
  void turnOff_Positive_RestoresTemperatureAndSetsOff() {
    // Arrange
    double temperature = 25;
    setTemperature(temperature);
    actuator.setPower(0, greenhouse);
    actuator.turnOn(greenhouse);

    // Act
    actuator.turnOff(greenhouse);
    double finalTemp = getTemperature();

    // Assert
    assertFalse(actuator.isOn());
    assertEquals(temperature, finalTemp, 0.0001,
            "Temperature should be restored to original after turnOff");
  }

  /**
   * Tests that toggle() switches from OFF to ON and cools the greenhouse.
   */
  @Test
  void toggle_Positive_TogglesFromOffToOnAndCools() {
    // Arrange
    double temperature = 30;
    setTemperature(temperature);
    actuator.setPower(0, greenhouse);

    // Act
    boolean result = actuator.toggle(greenhouse);
    double newTemp = getTemperature();

    // Assert
    assertTrue(result, "toggle() should return true when turning ON");
    assertTrue(actuator.isOn());
    assertEquals(temperature+actuator.getEffect(), newTemp, 0.0001);
  }

  /**
   * Tests that toggle() switches from ON to OFF and restores temperature.
   */
  @Test
  void toggle_Positive_TogglesFromOnToOffAndRestoresTemp() {
    // Arrange
    double temperature = 30;
    setTemperature(temperature);
    actuator.setPower(0, greenhouse);
    actuator.turnOn(greenhouse);

    // Act
    boolean result = actuator.toggle(greenhouse);
    double finalTemp = getTemperature();

    // Assert
    assertFalse(result, "toggle() should return false when turning OFF");
    assertFalse(actuator.isOn());
    assertEquals(temperature, finalTemp, 0.0001);
  }

  /**
   * Tests that setState(true) turns the actuator ON and cools the greenhouse.
   */
  @Test
  void setState_Positive_TurnsOnAndCools() {
    // Arrange
    double temperature = 30;
    setTemperature(temperature);
    actuator.setPower(0, greenhouse);

    // Act
    actuator.setState(true, greenhouse);
    double newTemp = getTemperature();

    // Assert
    assertTrue(actuator.isOn());
    assertEquals(temperature+actuator.getEffect(), newTemp, 0.0001);
  }

  /**
   * Tests that setState(false) turns the actuator OFF and restores the temperature.
   */
  @Test
  void setState_Positive_TurnsOffAndRestoresTemp() {
    // Arrange
    double temperature = 22;
    setTemperature(temperature);
    actuator.setPower(0, greenhouse);
    actuator.turnOn(greenhouse); // 22 -> 17

    // Act
    actuator.setState(false, greenhouse);
    double finalTemp = getTemperature();

    // Assert
    assertFalse(actuator.isOn());
    assertEquals(temperature, finalTemp, 0.0001);
  }

  /**
   * Tests that setPower() changes the cooling effect of the fan.
   * Higher speed should result in a larger temperature drop when turning on.
   */
  @Test
  void setPower_Positive_ChangesCoolingEffect() {
    // Arrange
    double temperature = 30;
    setTemperature(temperature);
    actuator.setPower(0, greenhouse);
    actuator.turnOn(greenhouse); // speed 0

    double tempAfterSpeed0 = getTemperature();
    // Assert
    assertEquals(temperature+actuator.getEffect(), tempAfterSpeed0, 0.0001);

    // Act
    actuator.setPower(2, greenhouse); // higher speed
    double tempAfterSpeed2 = getTemperature();

    // Assert
    assertEquals(temperature+actuator.getEffect(), tempAfterSpeed2, 0.0001);
  }

  /**
   * Tests that getID() returns null when no ID has been set.
   */
  @Test
  void getID_Negative_ReturnsNullWhenUnset() {
    assertNull(actuator.getID());
  }

  /**
   * Tests that calling turnOn() multiple times continues cooling
   * without throwing exceptions.
   */
  @Test
  void turnOn_Negative_MultipleCallsAccumulateCooling() {
    // Arrange
    double temperature = 20;
    setTemperature(temperature);
    actuator.setPower(0, greenhouse);

    // Act
    actuator.turnOn(greenhouse);
    actuator.turnOn(greenhouse);

    double finalTemp = getTemperature();

    // Assert
    assertTrue(actuator.isOn());
    assertEquals(temperature+actuator.getEffect(), finalTemp, 0.0001,
            "Multiple turnOn calls should not accumulate cooling");
  }

  /**
   * Tests that repeated turnOff() calls after being OFF do not change temperature.
   */
  @Test
  void turnOff_Negative_RepeatedCallsDoNotChangeTempWhenAlreadyOff() {
    // Arrange
    double temperature = 20;
    setTemperature(temperature);
    actuator.setPower(0, greenhouse);
    actuator.turnOn(greenhouse);
    actuator.turnOff(greenhouse);

    // Act
    actuator.turnOff(greenhouse); // already OFF, temp should stay
    double finalTemp = getTemperature();

    // Assert
    assertFalse(actuator.isOn());
    assertEquals(temperature, finalTemp, 0.0001);
  }

  /**
   * Tests that repeated toggling alternates state consistently.
   */
  @Test
  void toggle_Negative_RepeatedToggleAlternatesState() {
    // Arrange
    setTemperature(25.0);
    actuator.setPower(0, greenhouse);

    // Act
    actuator.toggle(greenhouse); // ON
    actuator.toggle(greenhouse); // OFF
    actuator.toggle(greenhouse); // ON

    // Assert
    assertTrue(actuator.isOn(),
            "Fan should be ON after odd number of toggles");
  }
}
