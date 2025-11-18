package actuator;

/**
 * Test implementation of {@link Actuator} used in unit tests, which are
 * needed to refactor SensorNodeTest Class.
 */
public class ActuatorTest implements Actuator {

  private final String id;
  private boolean on = false;
  private int power = 0;

  /**
   * Constructor for ActuatorTest.
   * @param id
   */
  public ActuatorTest(String id) {
    this.id = id;
  }

  /**
   * Returns the ID of the actuator.
   * @return
   */
  @Override
  public String getID() {
    return id;
  }

  /**
   * Returns the type of the actuator.
   * @return
   */
  @Override
  public String getType() {
    return "TestActuator";
  }

  /**
   * Returns whether the actuator is on.
   * @return
   */
  @Override
  public boolean isOn() {
    return on;
  }

  /**
   * Toggles the actuator on or off.
   * @return
   */
  @Override
  public boolean toggle() {
    on = !on;
    return on;
  }

  /**
   * Sets the power level of the actuator.
   * @param power
   */
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
