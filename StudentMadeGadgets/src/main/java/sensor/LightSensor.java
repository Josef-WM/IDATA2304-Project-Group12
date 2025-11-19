package sensor;

import greenhouse.Greenhouse;

/**
 * This class represents a light sensor in a greenhouse.
 * It implements the Sensor interface and provides methods to read
 * the light value from the greenhouse.
 */
public class LightSensor implements Sensor {
  private String ID;

  /**
   * Constructor for LightSensor.
   */
  public LightSensor() {
  }

  /**
   * Returns the ID of the sensor.
   * @return the ID of the sensor
   */
  @Override
  public String getID() {
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
  @Override
  public String getType() {
    return "Light";
  }

  /**
   * Returns the unit of the sensor.
   * @return the unit of the sensor
   */
  @Override
  public String getUnit() {
    return "Lux";
  }

  /**
   * Reads the light value from the greenhouse.
   * @return the light value
   */
  @Override
  public double read(Greenhouse greenhouse) {
    return (double)  greenhouse.getLight();
  }

}
