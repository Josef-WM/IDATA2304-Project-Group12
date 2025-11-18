package greenhouse;

import client.SensorNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Greenhouse class.
 *
 * <p>The internal timer is cancelled in tests to avoid nondeterministic
 * random updates to the environment. This test will test both negative and positive tests.</p>
 */
class GreenhouseTest {

  private Greenhouse greenhouse;

  @BeforeEach
  void setUp() {
    // Arrange
    greenhouse = new Greenhouse("TestHouse");
    cancelTimer();
  }

  /**
   * Cancels the internal timer so that automatic random updates do not interfere with tests.
   */
  private void cancelTimer() {
    try {
      Field timerField = Greenhouse.class.getDeclaredField("timer");
      timerField.setAccessible(true);
      Timer timer = (Timer) timerField.get(greenhouse);
      timer.cancel();
    } catch (Exception e) {
      fail("Failed to cancel timer via reflection: " + e.getMessage());
    }
  }

  /**
   * Helper method to set a double field on Greenhouse via reflection.
   *
   * @param fieldName
   * @param value
   */
  private void setDoubleField(String fieldName, double value) {
    try {
      Field field = Greenhouse.class.getDeclaredField(fieldName);
      field.setAccessible(true);
      field.setDouble(greenhouse, value);
    } catch (Exception e) {
      fail("Failed to set double field '" + fieldName + "': " + e.getMessage());
    }
  }

  /**
   * Helper method to set an int field on Greenhouse via reflection.
   *
   * @param fieldName
   * @param value
   */
  private void setIntField(String fieldName, int value) {
    try {
      Field field = Greenhouse.class.getDeclaredField(fieldName);
      field.setAccessible(true);
      field.setInt(greenhouse, value);
    } catch (Exception e) {
      fail("Failed to set int field '" + fieldName + "': " + e.getMessage());
    }
  }

  /**
   * Tests that the constructor sets initial values correctly.
   */
  @Test
  void constructor_Positive_SetsInitialValues() {
    // Arrange done in setUp()

    // Act
    String name = greenhouse.getGreenhouseName();
    double temp = greenhouse.getTemperature();
    int humidity = greenhouse.getHumidity();
    int light = greenhouse.getLight();

    // Assert
    assertEquals("TestHouse", name);
    assertEquals(14.0, temp, 0.0001);
    assertEquals(60, humidity);
    assertEquals(1000, light);
  }

  /**
   * Tests that setGreenhouseName changes the name correctly.
   */
  @Test
  void setGreenhouseName_Positive_ChangesName() {
    // Arrange

    // Act
    greenhouse.setGreenhouseName("NewName");

    // Assert
    assertEquals("NewName", greenhouse.getGreenhouseName());
  }

  /**
   * Tests that setGreenhouseName allows setting the name to null.
   */
  @Test
  void setGreenhouseName_Negative_AllowsNullName() {
    // Arrange

    // Act
    greenhouse.setGreenhouseName(null);

    // Assert
    assertNull(greenhouse.getGreenhouseName(),
            "Greenhouse should allow setting name to null (no validation implemented)");
  }

  /**
   * Tests that changeTemperature correctly updates the temperature.
   */
  @Test
  void changeTemperature_Positive_IncreasesTemperature() {
    // Arrange
    setDoubleField("temperature", 10.0);

    // Act
    greenhouse.changeTemperature(5.5);

    // Assert
    assertEquals(15.5, greenhouse.getTemperature(), 0.0001);
  }

  /**
   * Tests that changeTemperature correctly decreases the temperature.
   */
  @Test
  void changeTemperature_Negative_DecreasesTemperature() {
    // Arrange
    setDoubleField("temperature", 10.0);

    // Act
    greenhouse.changeTemperature(-3.0);

    // Assert
    assertEquals(7.0, greenhouse.getTemperature(), 0.0001);
  }

  /**
   * Tests that changeHumidity correctly updates the humidity within bounds.
   */
  @Test
  void changeHumidity_Positive_WithinBounds() {
    // Arrange
    setIntField("humidity", 50);

    // Act
    greenhouse.changeHumidity(10);

    // Assert
    assertEquals(60, greenhouse.getHumidity());
  }

  /**
   * Tests that changeHumidity does not exceed upper bound of 100 or go below 0.
   */
  @Test
  void changeHumidity_Negative_DoesNotExceedUpperBound() {
    // Arrange
    setIntField("humidity", 95);

    // Act
    greenhouse.changeHumidity(20); // attempt to go beyond 100

    // Assert
    assertTrue(greenhouse.getHumidity() <= 100,
            "Humidity should not exceed 100 even after large positive change");
  }

  /**
   * Tests that changeHumidity does not go below 0.
   */
  @Test
  void changeHumidity_Negative_DoesNotGoBelowZero() {
    // Arrange
    setIntField("humidity", 5);

    // Act
    greenhouse.changeHumidity(-20); // attempt to go below 0

    // Assert
    assertTrue(greenhouse.getHumidity() >= 0,
            "Humidity should not go below 0 even after large negative change");
  }

  /**
   * Tests that changeLight correctly updates the light level.
   */
  @Test
  void changeLight_Positive_IncreasesLight() {
    // Arrange
    setIntField("light", 1000);

    // Act
    greenhouse.changeLight(500);

    // Assert
    assertEquals(1500, greenhouse.getLight());
  }

  /**
   * Tests that changeLight correctly decreases the light level.
   */
  @Test
  void changeLight_Negative_AllowsDecreaseBelowInitial() {
    // Arrange
    setIntField("light", 500);

    // Act
    greenhouse.changeLight(-200);

    // Assert
    assertEquals(300, greenhouse.getLight());
  }

  /**
   * Tests that adding a SensorNode increases the internal list size.
   */
  @Test
  void addSensorNode_Positive_IncreasesListSize() {
    // Arrange
    SensorNode node = new SensorNode(greenhouse);

    // Act
    greenhouse.addSensorNodeToGreenhouse(node);

    // Assert
    try {
      Field listField = Greenhouse.class.getDeclaredField("sensorNodes");
      listField.setAccessible(true);
      @SuppressWarnings("unchecked")
      ArrayList<SensorNode> list = (ArrayList<SensorNode>) listField.get(greenhouse);
      assertEquals(1, list.size());
    } catch (Exception e) {
      fail("Failed to read sensorNodes via reflection: " + e.getMessage());
    }
  }

  /**
   * Tests that removing a SensorNode decreases the internal list size.
   */
  @Test
  void removeSensorNode_Negative_RemovingUnknownNodeDoesNotCrash() {
    // Arrange
    SensorNode node = new SensorNode(greenhouse);

    // Act
    greenhouse.removeSensorNodeFromGreenhouse(node);

    // Assert
    try {
      Field listField = Greenhouse.class.getDeclaredField("sensorNodes");
      listField.setAccessible(true);
      @SuppressWarnings("unchecked")
      ArrayList<SensorNode> list = (ArrayList<SensorNode>) listField.get(greenhouse);
      assertEquals(0, list.size(),
              "Removing a node that was never added should leave the list size at 0");
    } catch (Exception e) {
      fail("Failed to read sensorNodes via reflection: " + e.getMessage());
    }
  }
}
