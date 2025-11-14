package sensor;

import greenhouse.Greenhouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link TemperatureSensor} class.
 *
 * <p>This test class validates the following:</p>
 * <ul>
 *     <li>The sensor correctly exposes its ID</li>
 *     <li>The sensor reports the proper type ("Temperature")</li>
 *     <li>The sensor reports the proper unit ("Celsius")</li>
 *     <li>The sensor reads the temperature value supplied by the associated {@link Greenhouse}</li>
 * </ul>
 */
class TemperatureSensorTest {

  private Greenhouse greenhouse;
  private TemperatureSensor sensor;

  /**
   * Initializes a new {@link Greenhouse} instance and a {@link TemperatureSensor}
   * before each test.
   *
   * <p>The greenhouse starts with a default temperature of 14°C, so tests that
   * need a different value adjust it using {@code changeTemperature(delta)}.</p>
   */
  @BeforeEach
  void setUp() {
    greenhouse = new Greenhouse("TestHouse");
    sensor = new TemperatureSensor("temp1", greenhouse);
  }

  /**
   * Ensures that {@link TemperatureSensor#getID()} returns the ID supplied in the constructor.
   */
  @Test
  void testGetID() {
    assertEquals("temp1", sensor.getID(),
            "Sensor ID should match the constructor argument");
  }

  /**
   * Ensures that {@link TemperatureSensor#getType()} returns the correct sensor type.
   */
  @Test
  void testGetType() {
    assertEquals("Temperature", sensor.getType(),
            "Sensor type must be 'Temperature'");
  }

  /**
   * Ensures that {@link TemperatureSensor#getUnit()} returns the correct unit.
   */
  @Test
  void testGetUnit() {
    assertEquals("Celsius", sensor.getUnit(),
            "TemperatureSensor unit must be 'Celsius'");
  }

  /**
   * Verifies that TemperatureSensor returns the temperature from the greenhouse.
   *
   * <p>The greenhouse starts at 14°C, so we increase the temperature by +6.5°C, since it has variation or temperature random
   * by a few decimals it will sometimes fail, which are expected. </p>
   */
  @Test
  void testReadReturnsCurrentGreenhouseTemperature() {
    // Greenhouse initial temperature = 14°C
    greenhouse.changeTemperature(6.5); // Now expected temperature = 20.5°C

    double value = sensor.read();

    assertEquals(20.5, value, 0.0001,
            "Sensor should return the updated greenhouse temperature");
  }
}
