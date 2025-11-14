package actuator;

import greenhouse.Greenhouse;

/**
 * Represents a fan, acts as an actuator in a greenhouse.
 */
public class FanActuator implements Actuator {
  private Greenhouse greenhouse;
  private final String id;
  private boolean isOn;
  private int speed;
  private double heatDifference;

  /**
   * constructor for the FanActuator class.
   */
  public FanActuator(String id, Greenhouse greenhouse) {
    this.id = id;
    this.isOn = false;
    this.speed = 0;
    this.greenhouse = greenhouse;
    this.heatDifference = 0;

  }

  /**
   * Returns the id of the fan
   */
  public String getID() {
    return this.id;
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
   * And off if on
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

  public boolean isOn() {
    return this.isOn;
  }

  /**
   * sets the speed of the fan actuator.
   */
  public void setPower(int power) {
    this.speed = power;
  }
}