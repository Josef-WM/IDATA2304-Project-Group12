package sensor;

/**
 * Test implementation of {@link Sensor} used in unit tests.
 */
public class SensorTest implements Sensor {

  private final String id;
  private final double value;

  public SensorTest(String id, double value) {
    this.id = id;
    this.value = value;
  }

  @Override
  public String getID() {
    return id;
  }

  @Override
  public String getType() {
    return "TestSensor";
  }

  @Override
  public String getUnit() {
    return "units";
  }

  @Override
  public double read() {
    return value;
  }
}
