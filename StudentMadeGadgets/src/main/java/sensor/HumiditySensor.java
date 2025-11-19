package sensor;

import greenhouse.Greenhouse;

/**
 * This class represents a humidity sensor in a greenhouse.
 * It implements the Sensor interface and provides methods to read
 * the humidity value from the greenhouse.
 */
public class HumiditySensor implements Sensor {
  private String ID;
  private final Greenhouse greenhouse;

  /**
   * Constructor for HumiditySensor.
   * @param greenhouse the greenhouse to read humidity from
   */
  public HumiditySensor(Greenhouse greenhouse) {
    this.greenhouse = greenhouse;
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
    return "Humidity";
  }

  /**
   * Returns the unit of the sensor.
   * @return the unit of the sensor
   */
  @Override
  public String getUnit() {
    return "Percentage";
  }

  /**
   * Reads the humidity value from the greenhouse.
   * @return the humidity value
   */
  @Override
  public double read() {
    return (double) greenhouse.getHumidity();
  }
}
