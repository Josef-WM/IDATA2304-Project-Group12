package actuator;

import greenhouse.Greenhouse;

/**
 * Test implementation of {@link Actuator} used in unit tests, which are
 * needed to refactor SensorNodeTest Class.
 */
public class ActuatorTest implements Actuator {

  private String ID;
  private boolean on = false;
  private int power = 0;

  /**
   * Constructor for ActuatorTest.
   * @param ID
   */
  public ActuatorTest(String ID) {
    this.ID = ID;
  }

  /**
   * Returns the ID of the actuator.
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
  public boolean toggle(Greenhouse greenhouse) {
    on = !on;
    return on;
  }

  public void setState(boolean state, Greenhouse greenhouse) {
   this.on = state;
  }

  /**
   * Sets the power level of the actuator.
   * @param power
   */
  @Override
  public void setPower(int power, Greenhouse greenhouse) {
    this.power = power;
  }

  /**
   * Returns the current power level for assertions in tests.
   */
  public int getPower() {
    return power;
  }
}
