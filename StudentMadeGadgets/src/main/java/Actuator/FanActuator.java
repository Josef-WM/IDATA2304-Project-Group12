package Actuator;

import Greenhouse.Greenhouse;

/**
 * Represents a fan, acts as an actuator in a greenhouse.
 */
public class FanActuator implements Actuator {
  private Greenhouse greenhouse;
  private final int id;
  private boolean isOn;
  private float speed;

  /**
   * constructor for the FanActuator class.
   */
  public FanActuator(int id, Greenhouse greenhouse) {
    this.id = id;
    this.isOn = false;
    this.speed = 0;
    this.greenhouse = greenhouse;
  }

  /**
   * turns on the fan actuator.
   */
  public void turnOn() {
    greenhouse.changeTemperature(-5);
    isOn = true;
  }

  /**
   * turns off the fan actuator.
   */
  public void turnOff() {
    greenhouse.changeTemperature(5);
    isOn = false;
  }

  public boolean isOn() {
    return this.isOn;
  }

  /**
   * sets the speed of the fan actuator.
   */
  public void setSpeed(float speed) {
    this.speed = speed;
  }
}