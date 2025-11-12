package Actuator;

import Greenhouse.Greenhouse;

/**
 * Represents a fan, acts as an actuator in a greenhouse
 */
public class FanActuator implements Actuator {
  private Greenhouse greenhouse;
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
    greenhouse.changeTemperature(greenhouse.getTemperature() - 5);
    isOn = true;
  }

  /**
   * turns off the fan actuator
   */
  public void turnOff() {
    greenhouse.changeTemperature(greenhouse.getTemperature() + 5);
    isOn = false;
    }
}