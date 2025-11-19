package actuator;

import greenhouse.Greenhouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link LightActuator} class.
 *
 * <p>Tests validate the following functionality:
 * <ul>
 *     <li>ID assignment and retrieval</li>
 *     <li>Initial OFF state</li>
 *     <li>turnOn() and turnOff()</li>
 *     <li>toggle() behavior</li>
 *     <li>setState(boolean)</li>
 *     <li>Negative tests for repeated operations and unset ID</li>
 * </ul>
 * </p>
 *
 * <p>Greenhouse timer is disabled during tests for deterministic behavior.</p>
 */
class LightActuatorTest {

  private LightActuator actuator;
  private Greenhouse greenhouse;

  /**
   * Initializes a new LightActuator and a deterministic Greenhouse
   * instance before each test.
   */
  @BeforeEach
  void setUp() {
    actuator = new LightActuator();
    greenhouse = new Greenhouse("TestHouse");
    disableTimer();
  }

  /**
   * Disables the automatic timer in Greenhouse to prevent random light changes.
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
   * Tests that getType() returns "Light".
   */
  @Test
  void getType_Positive_ReturnsLight() {
    assertEquals("Light", actuator.getType());
  }

  /**
   * Tests that ID is stored and returned correctly.
   */
  @Test
  void setAndGetID_Positive_StoresIDCorrectly() {
    // Arrange
    actuator.setID("Light-1");

    // Act
    String id = actuator.getID();

    // Assert
    assertEquals("Light-1", id);
  }

  /**
   * Tests that the actuator starts OFF by default.
   */
  @Test
  void constructor_Positive_StartsOff() {
    assertFalse(actuator.isOn(), "Light actuator should start OFF");
  }

  /**
   * Tests that turnOn() sets the actuator to ON.
   */
  @Test
  void turnOn_Positive_SetsStateToOn() {
    actuator.turnOn(greenhouse);
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
   * Tests that toggle() switches state from OFF to ON.
   */
  @Test
  void toggle_Positive_TogglesFromOffToOn() {
    // Act
    boolean result = actuator.toggle(greenhouse);

    // Assert
    assertTrue(result);
    assertTrue(actuator.isOn());
  }

  /**
   * Tests that toggle() switches state from ON to OFF.
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
    actuator.setState(true, greenhouse);
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
   * Tests that getID() returns null when ID has not been assigned.
   */
  @Test
  void getID_Negative_ReturnsNullWhenUnset() {
    assertNull(actuator.getID());
  }

  /**
   * Tests that calling turnOff() multiple times keeps actuator OFF.
   */
  @Test
  void turnOff_Negative_MultipleCallsKeepStateOff() {
    actuator.turnOff(greenhouse);
    actuator.turnOff(greenhouse);
    assertFalse(actuator.isOn());
  }

  /**
   * Tests that calling turnOn() multiple times keeps actuator ON.
   */
  @Test
  void turnOn_Negative_MultipleCallsKeepStateOn() {
    actuator.turnOn(greenhouse);
    actuator.turnOn(greenhouse);
    assertTrue(actuator.isOn());
  }

  /**
   * Tests that repeated toggle() calls alternate between states correctly.
   */
  @Test
  void toggle_Negative_RepeatedToggleFlipsState() {
    actuator.toggle(greenhouse); // ON
    actuator.toggle(greenhouse); // OFF
    actuator.toggle(greenhouse); // ON

    assertTrue(actuator.isOn(), "Final state should be ON after odd toggles");
  }
}
