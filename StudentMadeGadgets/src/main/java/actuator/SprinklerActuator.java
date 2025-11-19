package actuator;

import greenhouse.Greenhouse;

/**
 * Sprinkler actuator class that implements the Actuator interface.
 * will be used to control the sprinkler in the greenhouse.
 */
public class SprinklerActuator implements Actuator {
  private String ID;
  private boolean isOn;

  /**
   * constructor for the SprinklerActuator class.
   */
  public SprinklerActuator() {
    this.isOn = false;
  }

  /**
   * Returns the id of the sprinkler.
   */
  @Override
  public String getID() {
    return this.ID;
  }

  /**
   * Sets the id of the sprinkler.
   * @param ID
   */
  @Override
  public void setID(String ID) {
    this.ID = ID;
  }

  /**
   * Returns the type of the actuator
   */
  @Override
  public String getType() {
    return "Light";
  }

  /**
   * Returns whether the actuator is on.
   */
  @Override
  public boolean isOn() {
    return this.isOn;
  }

  /**
   * turns on the actuator.
   */
  public void turnOn(Greenhouse greenhouse) {
    isOn = true;
  }

  /**
   * turns off the actuator.
   */
  public void turnOff(Greenhouse greenhouse) {
    isOn = false;
  }

  /**
   * Toggles the actuator
   * Turning it on if off
   * And off if on.
   */
  public boolean toggle(Greenhouse greenhouse) {
    if (isOn) {
      turnOff(greenhouse);
      return false;
    } else {
      turnOn(greenhouse);
      return true;
    }
  }

  /**
   * Sets the state of the actuator.
   * @param state
   * @param greenhouse
   */
  public void setState(boolean state, Greenhouse greenhouse) {
    if (state) {
      turnOn(greenhouse);
    } else {
      turnOff(greenhouse);
    }
  }
}
