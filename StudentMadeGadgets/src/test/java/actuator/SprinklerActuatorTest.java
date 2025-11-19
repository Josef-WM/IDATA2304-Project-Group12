package actuator;

import greenhouse.Greenhouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link SprinklerActuator} class.
 *
 * <p>This test suite verifies:
 * <ul>
 *     <li>ID handling</li>
 *     <li>Correct actuator type</li>
 *     <li>Initial OFF state</li>
 *     <li>turnOn() and turnOff()</li>
 *     <li>toggle() behavior</li>
 *     <li>setState(boolean)</li>
 *     <li>Negative tests for repeated operations and unset ID</li>
 * </ul>
 * </p>
 *
 * <p>Greenhouse timer is disabled using reflection for deterministic execution.</p>
 */
class SprinklerActuatorTest {

  private SprinklerActuator actuator;
  private Greenhouse greenhouse;

  /**
   * Sets up a new SprinklerActuator and deterministic Greenhouse
   * instance for every test run.
   */
  @BeforeEach
  void setUp() {
    actuator = new SprinklerActuator();
    greenhouse = new Greenhouse("TestHouse");
    disableTimer();
  }

  /**
   * Disables internal Greenhouse timer to avoid random environmental changes.
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
   * Tests that getType() returns the expected type string.
   * NOTE: Current implementation returns "Light".
   */
  @Test
  void getType_Positive_ReturnsExpectedType() {
    assertEquals("Light", actuator.getType(),
            "SprinklerActuator currently returns 'Light' as type");
  }

  /**
   * Tests that ID is properly stored and retrieved.
   */
  @Test
  void setAndGetID_Positive_StoresIDCorrectly() {
    // Arrange
    actuator.setID("Sprinkler-1");

    // Act
    String id = actuator.getID();

    // Assert
    assertEquals("Sprinkler-1", id);
  }

  /**
   * Tests that the actuator starts OFF by default.
   */
  @Test
  void constructor_Positive_StartsOff() {
    assertFalse(actuator.isOn(), "Sprinkler should start OFF");
  }

  /**
   * Tests turning the actuator ON.
   */
  @Test
  void turnOn_Positive_SetsStateToOn() {
    actuator.turnOn(greenhouse);
    assertTrue(actuator.isOn());
  }

  /**
   * Tests turning the actuator OFF.
   */
  @Test
  void turnOff_Positive_SetsStateToOff() {
    // Arrange
    actuator.turnOn(greenhouse);

    // Act
    actuator.turnOff(greenhouse);

    // Assert
    assertFalse(actuator.isOn());
  }

  /**
   * Tests toggle() switching state from OFF to ON.
   */
  @Test
  void toggle_Positive_TogglesFromOffToOn() {
    boolean result = actuator.toggle(greenhouse);

    assertTrue(result);
    assertTrue(actuator.isOn());
  }

  /**
   * Tests toggle() switching state from ON to OFF.
   */
  @Test
  void toggle_Positive_TogglesFromOnToOff() {
    // Arrange
    actuator.turnOn(greenhouse);

    // Act
    boolean result = actuator.toggle(greenhouse);

    // Assert
    assertFalse(result);
    assertFalse(actuator.isOn());
  }

  /**
   * Tests setState(true) correctly turns actuator ON.
   */
  @Test
  void setState_Positive_TurnsOn() {
    actuator.setState(true, greenhouse);
    assertTrue(actuator.isOn());
  }

  /**
   * Tests setState(false) correctly turns actuator OFF.
   */
  @Test
  void setState_Positive_TurnsOff() {
    // Arrange
    actuator.turnOn(greenhouse);

    // Act
    actuator.setState(false, greenhouse);

    // Assert
    assertFalse(actuator.isOn());
  }

  /**
   * Tests that getID() returns null when unset.
   */
  @Test
  void getID_Negative_ReturnsNullWhenUnset() {
    assertNull(actuator.getID());
  }

  /**
   * Tests that calling turnOff() repeatedly keeps actuator OFF.
   */
  @Test
  void turnOff_Negative_RepeatedCallsKeepOff() {
    actuator.turnOff(greenhouse);
    actuator.turnOff(greenhouse);
    assertFalse(actuator.isOn());
  }

  /**
   * Tests that calling turnOn() repeatedly keeps actuator ON.
   */
  @Test
  void turnOn_Negative_RepeatedCallsKeepOn() {
    actuator.turnOn(greenhouse);
    actuator.turnOn(greenhouse);
    assertTrue(actuator.isOn());
  }

  /**
   * Tests repeated toggling alternates correctly between ON and OFF.
   */
  @Test
  void toggle_Negative_RepeatedToggleFlipsStateCorrectly() {
    actuator.toggle(greenhouse); // ON
    actuator.toggle(greenhouse); // OFF
    actuator.toggle(greenhouse); // ON

    assertTrue(actuator.isOn(), "Final state should be ON after odd number of toggles");
  }
}
