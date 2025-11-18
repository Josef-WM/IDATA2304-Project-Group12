package actuator;

import greenhouse.Greenhouse;

/**
 * Represents a fan, acts as an actuator in a greenhouse.
 */
public class FanActuator implements Actuator {
  private final Greenhouse greenhouse;
  private String ID;
  private boolean isOn;
  private int speed;
  private double heatDifference;

  /**
   * constructor for the FanActuator class.
   */
  public FanActuator(String ID, Greenhouse greenhouse) {
    this.ID = ID;
    this.isOn = false;
    this.speed = 0;
    this.greenhouse = greenhouse;
    this.heatDifference = 0;

  }

  /**
   * Returns the type of the actuator
   */
  public String getType() {
    return "Fan";
  }

  /**
   * Returns the id of the fan.
   */
  public String getID() {
    return this.ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  /**
   * turns on the fan actuator.
   */
  public void turnOn() {
    this.heatDifference = this.heatDifference - 5*(1+speed*0.2);
    greenhouse.changeTemperature(this.heatDifference);
    isOn = true;
  }

  /**
   * turns off the fan actuator.
   */
  public void turnOff() {
    greenhouse.changeTemperature(-this.heatDifference);
    this.heatDifference = 0;
    isOn = false;
  }

  /**
   * Toggles the fan
   * Turning it on if off
   * And off if on.
   */
  public boolean toggle() {
    if (isOn) {
      turnOff();
      return false;
    } else {
      turnOn();
      return true;
    }
  }

  /**
   * Returns the isOn() state.
   *
   * @return boolean isOn() state
   */
  public boolean isOn() {
    return this.isOn;
  }

  /**
   * Gets the speed of the fan.
   */
  public float getSpeed() {
    return this.speed;
  }

  /**
   * sets the speed of the fan actuator.
   */
  public void setPower(int speed) {
    this.speed = speed;
  }

  /**
   * Gets the power of the fan actuator.
   */
  public int getPower() {
    return this.speed;
  }
}