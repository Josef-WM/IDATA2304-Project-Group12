package actuator;

/**
 * Test implementation of {@link Actuator} used in unit tests.
 */
public class ActuatorTest implements Actuator {

  private final String id;
  private boolean on = false;
  private int power = 0;

  public ActuatorTest(String id) {
    this.id = id;
  }

  @Override
  public String getID() {
    return id;
  }

  @Override
  public String getType() {
    return "TestActuator";
  }

  @Override
  public boolean isOn() {
    return on;
  }

  @Override
  public boolean toggle() {
    on = !on;
    return on;
  }

  @Override
  public void setPower(int power) {
    this.power = power;
  }

  /**
   * Returns the current power level for assertions in tests.
   */
  public int getPower() {
    return power;
  }
}
