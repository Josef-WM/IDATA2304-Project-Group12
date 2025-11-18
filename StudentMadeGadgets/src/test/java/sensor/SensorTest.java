package sensor;

/**
 * Test implementation of {@link Sensor} used in unit tests, which are needed to refactor
 * the SensorNodeTest Class.
 */
public class SensorTest implements Sensor {

  private final String id;
  private final double value;

  /**
   * Constructor for SensorTest.
   * @param id
   * @param value
   */
  public SensorTest(String id, double value) {
    this.id = id;
    this.value = value;
  }

  /**
   * Returns the ID of the sensor.
   * @return
   */
  @Override
  public String getID() {
    return id;
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
