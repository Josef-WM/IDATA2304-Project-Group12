package sensor;

import greenhouse.Greenhouse;

/**
 * This class represents a temperature sensor in a greenhouse.
 * It implements the Sensor interface and provides methods to read
 * the temperature value from the greenhouse.
 */
public class TemperatureSensor implements Sensor {
  private String ID;

  /**
   * Constructor for TemperatureSensor.
   */
  public TemperatureSensor() {
  }

  /**
   * Returns the ID of the sensor.
   * @return the ID of the sensor
   */
  @Override public String getID() {
    return ID;
  }

  /**
   * Sets the ID of the sensor.
   * @param ID the ID to set
   */
  @Override
  public void setID(String ID) {
    this.ID = ID;
  }

  /**
   * Returns the type of the sensor.
   * @return the type of the sensor
   */
  @Override public String getType() {
    return "Temperature";
  }

  /**
   * Returns the unit of the sensor.
   * @return the unit of the sensor
   */
  @Override public String getUnit() {
    return "Celsius";
  }

  /**
   * Reads the temperature value from the greenhouse.
   * @return the temperature value
   */
  @Override public double read(Greenhouse greenhouse) {
    return greenhouse.getTemperature();
  }
}
