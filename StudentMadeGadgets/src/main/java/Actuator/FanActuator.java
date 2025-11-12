package Actuator;

/**
 * Represents a fan, acts as an actuator in a greenhouse
 */
public class FanActuator {
  private boolean isOn;
  private float speed;

  /**
   * constructor for the FanActuator class
   */
  public FanActuator(boolean isOn, float speed) {
    this.isOn = isOn;
    this.speed = speed;
  }

  /**
   * turns on the fan actuator
   */
  public void turnOn() {
    isOn = true;
  }

  /**
   * turns off the fan actuator
   */
  public void turnOff() {
    isOn = false;
    }
}