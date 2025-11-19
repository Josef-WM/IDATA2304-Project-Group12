package actuator;

import greenhouse.Greenhouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link HeaterActuator} class.
 *
 * <p>This test suite validates all actuator behavior including:
 * <ul>
 *     <li>ID handling</li>
 *     <li>getType()</li>
 *     <li>Initial OFF state</li>
 *     <li>turnOn() / turnOff()</li>
 *     <li>toggle() behavior</li>
 *     <li>setState()</li>
 *     <li>Negative tests for unset ID and repeated calls</li>
 * </ul>
 * </p>
 *
 * <p>Greenhouse internal timer is disabled to avoid nondeterministic tests.</p>
 */
class HeaterActuatorTest {

  private HeaterActuator actuator;
  private Greenhouse greenhouse;

  /**
   * Sets up a deterministic Greenhouse and a fresh HeaterActuator.
   */
  @BeforeEach
  void setUp() {
    actuator = new HeaterActuator();
    greenhouse = new Greenhouse("TestHouse");
    disableTimer();
  }

  /**
   * Disables the automatic timer inside Greenhouse.
   * Prevents random temperature/light updates during tests.
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
   * Tests that the heater reports the correct type.
   * Expected: "Heater".
   */
  @Test
  void getType_Positive_ReturnsHeater() {
    assertEquals("Heater", actuator.getType());
  }

  /**
   * Tests that the actuator ID is stored and retrieved correctly.
   */
  @Test
  void setAndGetID_Positive_StoresIDCorrectly() {
    // Arrange
    actuator.setID("Heater-1");

    // Act
    String id = actuator.getID();

    // Assert
    assertEquals("Heater-1", id);
  }

  /**
   * Tests that the actuator starts in the OFF state.
   * Expected: isOn() = false.
   */
  @Test
  void constructor_Positive_StartsOff() {
    assertFalse(actuator.isOn(), "Heater should start OFF");
  }

  /**
   * Tests that turnOn() sets the actuator to ON.
   */
  @Test
  void turnOn_Positive_SetsStateToOn() {
    // Arrange

    // Act
    actuator.turnOn(greenhouse);

    // Assert
    assertTrue(actuator.isOn());
  }

  /**
   * Tests that turnOff() sets the actuator to OFF.
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
   * Tests that toggle() turns the actuator ON when it is OFF.
   * Expected: returns true, isOn() = true.
   */
  @Test
  void toggle_Positive_TogglesFromOffToOn() {
    // Arrange

    // Act
    boolean result = actuator.toggle(greenhouse);

    // Assert
    assertTrue(result);
    assertTrue(actuator.isOn());
  }

  /**
   * Tests that toggle() turns the actuator OFF when it is ON.
   * Expected: returns false, isOn() = false.
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
   * Tests that setState(true) turns the actuator ON.
   */
  @Test
  void setState_Positive_TurnsOn() {
    // Arrange

    // Act
    actuator.setState(true, greenhouse);

    // Assert
    assertTrue(actuator.isOn());
  }

  /**
   * Tests that setState(false) turns the actuator OFF.
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
   * Tests that getID() returns null if no ID was ever set.
   * Expected: null.
   */
  @Test
  void getID_Negative_ReturnsNullWhenUnset() {
    assertNull(actuator.getID());
  }

  /**
   * Tests that calling turnOff() multiple times does not break state.
   * Expected: still OFF.
   */
  @Test
  void turnOff_Negative_MultipleCallsKeepStateOff() {
    // Arrange

    // Act
    actuator.turnOff(greenhouse);
    actuator.turnOff(greenhouse);

    // Assert
    assertFalse(actuator.isOn());
  }

  /**
   * Tests that calling turnOn() multiple times does not break state.
   * Expected: still ON.
   */
  @Test
  void turnOn_Negative_MultipleCallsKeepStateOn() {
    // Arrange

    // Act
    actuator.turnOn(greenhouse);
    actuator.turnOn(greenhouse);

    // Assert
    assertTrue(actuator.isOn());
  }

  /**
   * Tests toggling repeatedly maintains proper alternating behavior.
   */
  @Test
  void toggle_Negative_RepeatedToggleFlipsState() {
    // Arrange

    // Act
    actuator.toggle(greenhouse); // ON
    actuator.toggle(greenhouse); // OFF
    actuator.toggle(greenhouse); // ON

    // Assert
    assertTrue(actuator.isOn(), "Final state should be ON after odd number of toggles");
  }
}
