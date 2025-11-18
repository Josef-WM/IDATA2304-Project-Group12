package sensor;

/**
 * Test implementation of {@link Sensor} used in unit tests, which are needed to refactor
 * the SensorNodeTest Class.
 */
public class SensorTest implements Sensor {

  private String ID;
  private double value;

  /**
   * Constructor for SensorTest.
   * @param ID
   * @param value
   */
  public SensorTest(String ID, double value) {
    this.ID = ID;
    this.value = value;
  }

  /**
   * Returns the ID of the sensor.
   * @return
   */
  @Override
  public String getID() {
    return ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  /**
   * Returns the type of the sensor.
   * @return
   */
  @Override
  public String getType() {
    return "TestSensor";
  }

  /**
   * Returns the unit of the sensor.
   * @return
   */
  @Override
  public String getUnit() {
    return "units";
  }
/**
   * Returns the value of the sensor.
   * @return
   */
  @Override
  public double read() {
    return value;
  }
}
